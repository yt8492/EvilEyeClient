package com.a2p.evileye.client.login

import android.util.Log
import com.a2p.evileye.client.data.user.UserRepository

class LoginPresenter(private val userRepository: UserRepository,
                     private val view: LoginContract.View) : LoginContract.Presenter {
    init {
        view.presenter = this
    }

    override fun start() {
        userRepository.checkConnection { isConnected ->
            if (isConnected) {
                view.openVoteList()
                checkLogin()
            } else {
                Log.d(TAG, "health check failed")
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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        const val TAG = "Login Presenter"
    }
}