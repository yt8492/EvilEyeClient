package com.a2p.evileye.client.main

import com.a2p.evileye.client.data.EvilEyeService

class MainPresenter(private val evilEyeService: EvilEyeService,
                    private val tarekomiBoardView: MainContract.TarekomiBoardView,
                    private val searchView: MainContract.SearchView,
                    private val myPageView: MainContract.MyPageView) : MainContract.MainPresenter {
    init {
        tarekomiBoardView.presenter = this
        searchView.presenter = this
        myPageView.presenter = this
    }

    private val viewSwitchListeners = mutableListOf<ViewSwitchListener>()
    private var logoutListener: LogoutListener? = null

    override fun start() {

    }

    override fun tarekomi(url: String, userName: String, desc: String) {
        evilEyeService.tarekomi(url, userName, desc)
    }

    override fun showTarekomiBoardView() {
        viewSwitchListeners.forEach { listener ->
            listener(MainNavigationViewItem.TAREKOMI_BOARD)
        }
    }

    override fun listTarekomiSummaries() {
        val tarekomiSummaries = evilEyeService.getTarekomiSummaries()
        tarekomiBoardView.showTarekomiSummaries(tarekomiSummaries)
    }

    override fun showSearchView() {
        viewSwitchListeners.forEach { listener ->
            listener(MainNavigationViewItem.SEARCH)
        }
    }

    override fun search(query: String) {

    }

    override fun listUser() {
        val users = evilEyeService.getUsers()
        searchView.showSearchResult(users)
    }

    override fun vote(tarekomiId: Long, desc: String) {
        evilEyeService.vote(tarekomiId, desc)
    }

    override fun showMyPageView() {
        viewSwitchListeners.forEach { listener ->
            listener(MainNavigationViewItem.MY_PAGE)
        }
        val userInfo = evilEyeService.getMyInfo()
        myPageView.showMyPage(userInfo)
    }

    override fun addViewSwitchListener(listener: ViewSwitchListener) {
        viewSwitchListeners.add(listener)
    }

    override fun setOnLogoutListener(listener: LogoutListener) {
        logoutListener = listener
    }

    override fun logout() {
        evilEyeService.logout()
        logoutListener?.invoke()
    }
}