package com.a2p.evileye.client.login

import com.a2p.evileye.client.BasePresenter
import com.a2p.evileye.client.BaseView

interface LoginContract {
    interface Presenter : BasePresenter {
        fun checkLogin()
        fun login(userName: String, password: String)
    }

    interface View : BaseView<Presenter> {
        var isActive: Boolean

        fun openVoteList()
    }
}