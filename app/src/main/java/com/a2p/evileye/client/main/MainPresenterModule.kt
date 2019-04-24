package com.a2p.evileye.client.main

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance

fun mainPresenerModule(tarekomiBoardView: MainContract.TarekomiBoardView,
                       searchView: MainContract.SearchView,
                       myPageView: MainContract.MyPageView) = Kodein.Module {
    bind<MainContract.TarekomiBoardView>() with instance(tarekomiBoardView)
    bind<MainContract.SearchView>() with instance(searchView)
    bind<MainContract.MyPageView>() with instance(myPageView)
}