package com.a2p.evileye.client.data.user

interface UserDataSource {
    fun checkConnection(result: (Boolean) -> Unit)
    fun login(userName: String, password: String, result: (Boolean) -> Unit)
}