package com.a2p.evileye.client.data

import io.grpc.*

class AuthInterceptor(private val token: String) : ClientInterceptor {

    override fun <ReqT : Any?, RespT : Any?> interceptCall(
        method: MethodDescriptor<ReqT, RespT>?,
        callOptions: CallOptions?,
        next: Channel
    ): ClientCall<ReqT, RespT> {
        return object : ForwardingClientCall.SimpleForwardingClientCall<ReqT, RespT>(next.newCall(method, callOptions)) {
            override fun start(responseListener: Listener<RespT>?, headers: Metadata) {
                headers.put(CUSTOM_HEADER_KEY, "Bearer $token")
                super.start(responseListener, headers)
            }
        }
    }

    companion object {
        private val CUSTOM_HEADER_KEY = Metadata.Key.of("Authorization", Metadata.ASCII_STRING_MARSHALLER)
    }
}