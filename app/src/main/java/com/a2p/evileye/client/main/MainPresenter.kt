package com.a2p.evileye.client.main

import com.a2p.evileye.client.data.EvilEyeService

class MainPresenter(private val evilEyeService: EvilEyeService,
                    private val tarekomiBoardView: MainContract.TarekomiBoardView,
                    private val searchView: MainContract.SearchView,
                    private val myPageView: MainContract.MyPageView) : MainContract.MainPresenter {

    private val listeners = mutableListOf<ViewSwitchListener>()

    override fun start() {

    }

    override fun showTarekomiBoardView() {
        listeners.forEach { listener ->
            listener(MainNavigationViewItem.TAREKOMI_BOARD)
        }
    }

    override fun listTarekomi() {

    }

    override fun showSearchView() {
        listeners.forEach { listener ->
            listener(MainNavigationViewItem.SEARCH)
        }
    }

    override fun search(query: String) {

    }

    override fun showMyPageView() {
        listeners.forEach { listener ->
            listener(MainNavigationViewItem.MY_PAGE)
        }
    }

    override fun addViewSwitchListener(listener: ViewSwitchListener) {
        listeners.add(listener)
    }

}