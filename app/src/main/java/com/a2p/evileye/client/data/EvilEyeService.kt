package com.a2p.evileye.client.data

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.core.content.edit
import com.yt8492.evileye.protobuf.Empty
import com.yt8492.evileye.protobuf.LoginRequest
import com.yt8492.evileye.protobuf.PublicGrpc
import io.grpc.ManagedChannel
import io.grpc.StatusRuntimeException

class EvilEyeService(private val context: Context,
                     channel: ManagedChannel) {
    private val sharedPreferences by lazy {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }
    private val stub = PublicGrpc.newBlockingStub(channel)

    @SuppressLint("CheckResult")
    fun checkConnection(onSuccess: () -> Unit, onFailure: () -> Unit) {
        val req = Empty.newBuilder().build()
        try {
            stub.healthCheck(req) // call health check
            onSuccess()

        } catch (e: StatusRuntimeException) {
            if (e.status.description == "invalid unixtime") {
                onSuccess()
            } else {
                e.printStackTrace()
                onFailure()
            }
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
            val res = stub.login(req)
            Log.d(TAG, "token: ${res.token}")
            val token = res.token
            sharedPreferences.edit {
                putString(KEY_TOKEN, token)
            }
            onSuccess()
        } catch (e: Exception) {
            e.printStackTrace()
            onFailure()
        }
    }

    fun getUserToken(): String? {
        return sharedPreferences.getString(KEY_TOKEN, null)
    }

    companion object {
        private const val PREF_NAME = "EVIL_EYE"
        private const val KEY_TOKEN = "KEY_TOKEN"
        private const val TAG = "EvilEyeService"
    }
}