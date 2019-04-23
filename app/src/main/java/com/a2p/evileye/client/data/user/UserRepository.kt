package com.a2p.evileye.client.data.user

import android.content.Context
import com.yt8492.evileye.protobuf.Empty
import com.yt8492.evileye.protobuf.PublicGrpc
import io.grpc.ManagedChannel
import java.lang.Exception

class UserRepository(private val context: Context,
                     private val channel: ManagedChannel) : UserDataSource {
    private val sharedPreferences by lazy {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    override fun checkConnection(result: (Boolean) -> Unit) {
        val stub = PublicGrpc.newBlockingStub(channel)
        val req = Empty.newBuilder().build()
        try {
            val res = stub.healthCheck(req)
            result(res.commitHash.isNotBlank())
        } catch (e: Exception) {
            e.printStackTrace()
            result(false)
        }
    }

    fun getUserToken(): String? {
        return sharedPreferences.getString(KEY_TOKEN, null)
    }

    companion object {
        private const val PREF_NAME = "EVIL_EYE"
        private const val KEY_TOKEN = "KEY_TOKEN"
    }
}