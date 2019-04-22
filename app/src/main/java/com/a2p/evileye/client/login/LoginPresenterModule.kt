package com.a2p.evileye.client.login

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance

fun loginPresenterModule(view: LoginContract.View) = Kodein.Module {
    bind<LoginContract.View>() with instance(view)
}