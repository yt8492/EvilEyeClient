package com.a2p.evileye.client.main

import com.a2p.evileye.client.data.EvilEyeService
import com.yt8492.evileye.protobuf.Tarekomi
import com.yt8492.evileye.protobuf.TarekomiReq

class MainPresenter(private val evilEyeService: EvilEyeService,
                    private val tarekomiBoardView: MainContract.TarekomiBoardView,
                    private val searchView: MainContract.SearchView,
                    private val myPageView: MainContract.MyPageView) : MainContract.MainPresenter {
    init {
        tarekomiBoardView.presenter = this
        searchView.presenter = this
        myPageView.presenter = this
    }

    private val listeners = mutableListOf<ViewSwitchListener>()

    override fun start() {

    }

    override fun tarekomi(url: String, userName: String, desc: String) {
        evilEyeService.tarekomi(url, userName, desc)
    }

    override fun showTarekomiBoardView() {
        listeners.forEach { listener ->
            listener(MainNavigationViewItem.TAREKOMI_BOARD)
        }
    }

    override fun listTarekomiSummaries() {
        val tarekomiSummaries = evilEyeService.getTarekomiSummaries()
        tarekomiBoardView.showTarekomiSummaries(tarekomiSummaries)
    }

    override fun showSearchView() {
        listeners.forEach { listener ->
            listener(MainNavigationViewItem.SEARCH)
        }
    }

    override fun search(query: String) {

    }

    override fun vote(tarekomiId: Long, desc: String) {
        evilEyeService.vote(tarekomiId, desc)
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