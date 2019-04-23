package com.a2p.evileye.client.login

import com.a2p.evileye.client.data.user.UserRepository

class LoginPresenter(private val userRepository: UserRepository,
                     private val view: LoginContract.View) : LoginContract.Presenter {
    init {
        view.presenter = this
    }

    override fun start() {
        userRepository.checkConnection { isConnected ->
            if (isConnected) {
                checkLogin()
            } else {
                view.showConnectionFailure()
            }
        }
    }

    override fun checkLogin() {
        val token = userRepository.getUserToken()
        if (token != null) {
            view.openVoteList()
        }
    }

    override fun login(userName: String, password: String) {
        userRepository.login(userName, password) { loginSucceed ->
            if (loginSucceed) {
                view.openVoteList()
            } else {
                view.showLoginFailure()
            }
        }
    }

    companion object {
        const val TAG = "Login Presenter"
    }
}