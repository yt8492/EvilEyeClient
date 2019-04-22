package com.a2p.evileye.client.data.user

import com.yt8492.evileye.protobuf.PublicGrpc
import io.grpc.ManagedChannel

class UserRepository(private val channel: ManagedChannel) : UserDataSource {
    override fun checkConnection(result: (Boolean) -> Unit) {
        PublicGrpc.getHealthCheckMethod()
    }

}