package com.a2p.evileye.client.login

import com.a2p.evileye.client.data.user.UserRepository

class LoginPresenter(private val userRepository: UserRepository,
                     private val view: LoginContract.View) : LoginContract.Presenter {
    init {
        view.presenter = this
    }

    override fun start() {
        checkLogin()
    }

    override fun checkLogin() {
        userRepository.checkConnection { isConnected ->
            if (isConnected) {
                view.openVoteList()
            }
        }
    }

    override fun login(userName: String, password: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}