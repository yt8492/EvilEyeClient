package com.a2p.evileye.client.data

import android.app.Activity
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

    fun getTarekomiSummaries(): List<TarekomiSummary> {
        return DummyDatas.tarekomiSummaries
    }

    private fun getUserToken(): String? {
        return sharedPreferences.getString(KEY_TOKEN, null)
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

    fun vote(tarekomiId: Long, desc: String) {
        val req = VoteReq.newBuilder()
            .setTarekomiId(tarekomiId)
            .setDesc(desc)
            .build()

        try {
            val res = privateStub?.vote(req)
            Log.d(TAG, "res: ${res.toString()}")
        } catch (e: Exception) {
            e.printStackTrace()
            logoutAndFinishApp()
        }
    }

    private fun logoutAndFinishApp() {
        sharedPreferences.edit {
            putString(KEY_TOKEN, null)
        }
        (context as? Activity)?.finish()
    }

    companion object {
        private const val PREF_NAME = "EVIL_EYE"
        private const val KEY_TOKEN = "KEY_TOKEN"
        private const val TAG = "EvilEyeService"
    }
}