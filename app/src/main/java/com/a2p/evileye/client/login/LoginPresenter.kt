package com.a2p.evileye.client.login

import com.a2p.evileye.client.data.EvilEyeService

class LoginPresenter(private val evilEyeService: EvilEyeService,
                     private val view: LoginContract.View) : LoginContract.Presenter {
    init {
        view.presenter = this
    }

    override fun start() {
        evilEyeService.checkConnection(onSuccess = {
            checkLogin()
        }, onFailure = {
            view.showConnectionFailure()
        })
    }

    override fun checkLogin() {
        val token = evilEyeService.getUserToken()
        if (token != null) {
            view.openVoteList()
        }
    }

    override fun login(userName: String, password: String) {
        evilEyeService.login(userName, password, onSuccess = {
            view.openVoteList()
        }, onFailure = {
            view.showLoginFailure()
        })
    }

    companion object {
        const val TAG = "Login Presenter"
    }
}