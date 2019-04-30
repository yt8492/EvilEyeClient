package com.a2p.evileye.client.data

import android.content.Context
import android.util.Log
import androidx.core.content.edit
import com.yt8492.evileye.protobuf.*
import io.grpc.ManagedChannelBuilder

class EvilEyeService(private val context: Context,
                     private val ipAddress: String,
                     private val port: Int) {
    private val sharedPreferences by lazy {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }
    private val publicStub = PublicGrpc.newBlockingStub(ManagedChannelBuilder
        .forAddress(ipAddress, port)
        .usePlaintext()
        .build())
    private var privateStub: PrivateGrpc.PrivateBlockingStub? = null

    private fun getUserToken(): String? {
        return sharedPreferences.getString(KEY_TOKEN, null)
    }

    fun checkConnection(onSuccess: () -> Unit, onFailure: () -> Unit) {
        val req = Empty.newBuilder().build()
        try {
            val res = publicStub.healthCheck(req) // call health check
            Log.d(TAG, res.commitHash)
            onSuccess()
        } catch (e: Exception) {
            e.printStackTrace()
            onFailure()
        }
    }

    fun login(userName: String, password: String, onSuccess: () -> Unit, onFailure: () -> Unit) {
        val req = LoginRequest.newBuilder()
            .setScreenName(userName)
            .setPassword(password)
            .build()
        try {
            val res = publicStub.login(req)
            Log.d(TAG, "token: ${res.token}")
            val token = res.token
            sharedPreferences.edit {
                putString(KEY_TOKEN, token)
            }
            val privateChannel = ManagedChannelBuilder.forAddress(ipAddress, port)
                .intercept(AuthInterceptor(token))
                .usePlaintext()
                .build()
            privateStub = PrivateGrpc.newBlockingStub(privateChannel)
            onSuccess()
        } catch (e: Exception) {
            privateStub = null
            e.printStackTrace()
            onFailure()
        }
    }

    fun checkLogin(onSuccess: () -> Unit, onFailure: () -> Unit) {
        val token = getUserToken() ?: run {
            onFailure()
            return
        }
        try {
            val privateChannel = ManagedChannelBuilder.forAddress(ipAddress, port)
                .intercept(AuthInterceptor(token))
                .usePlaintext()
                .build()
            privateStub = PrivateGrpc.newBlockingStub(privateChannel)
            onSuccess()
        } catch (e: Exception) {
            privateStub = null
            e.printStackTrace()
            onFailure()
        }
    }

    fun tarekomi(url: String, userName: String, desc: String) {
        val tarekomi = Tarekomi.newBuilder()
            .setUrl(url)
            .setTargetUserName(userName)
            .setDesc(desc)
            .build()
        val req = TarekomiReq.newBuilder()
            .setTarekomi(tarekomi)
            .build()
        try {
            val res = privateStub?.tarekomi(req)
            Log.e(TAG, "tarekomiRes: $res")
        } catch (e: Exception)  {
            e.printStackTrace()
            logout()
        }
    }

    fun getTarekomiSummaries(): List<TarekomiSummary> {
        val req = TarekomiBoardReq.newBuilder()
            .setOffset(0)
            .setLimit(100)
            .build()
        privateStub?.let { stub ->
            try {
                val res = stub.tarekomiBoard(req)
                return res.tarekomisList
            } catch (e: Exception) {
                e.printStackTrace()
                logout()
            }
        }
        return listOf()
    }

    fun vote(tarekomiId: Long, desc: String) {
        val req = VoteReq.newBuilder()
            .setTarekomiId(tarekomiId)
            .setDesc(desc)
            .build()

        try {
            val res = privateStub?.vote(req)
            Log.d(TAG, "voteRes: $res")
        } catch (e: Exception) {
            e.printStackTrace()
            logout()
        }
    }

    fun getUsers(): List<User> {
        val req = GetUserListReq.newBuilder()
            .setLimit(100)
            .setOffset(0)
            .build()
        return try {
            privateStub?.getUserList(req)
                ?.usersList
                ?: error("users is null")
        } catch (e: Exception) {
            e.printStackTrace()
            listOf()
        }
    }

    fun getMyInfo(): User {
        val req = UserInfoReq.getDefaultInstance()
        return try {
            val userInfo = privateStub?.getUserInfo(req) ?: error("user info req failed")
            userInfo
        } catch (e: Exception) {
            e.printStackTrace()
            logout()
            User.getDefaultInstance()
        }
    }

    fun logout() {
        sharedPreferences.edit {
            putString(KEY_TOKEN, null)
        }
    }

    companion object {
        private const val PREF_NAME = "EVIL_EYE"
        private const val KEY_TOKEN = "KEY_TOKEN"
        private const val TAG = "EvilEyeService"
    }
}