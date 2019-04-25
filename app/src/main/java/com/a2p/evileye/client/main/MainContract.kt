package com.a2p.evileye.client.main

import com.a2p.evileye.client.BasePresenter
import com.a2p.evileye.client.BaseView
import com.yt8492.evileye.protobuf.TarekomiSummary
import com.yt8492.evileye.protobuf.User

typealias ViewSwitchListener = (MainNavigationViewItem) -> Unit

interface MainContract {
    interface MainPresenter : BasePresenter {
        fun showTarekomiBoardView()
        fun listTarekomiSummaries()
        fun showSearchView()
        fun search(query: String)
        fun showMyPageView()
        fun addViewSwitchListener(listener: ViewSwitchListener)
    }

    interface TarekomiBoardView : BaseView<MainPresenter> {
        var isActive: Boolean

        fun showTarekomiSummaries(tarekomiSummaries: List<TarekomiSummary>)
        fun showTarekomiView()
    }

    interface VoteView : BaseView<MainPresenter> {
        fun vote()
    }

    interface SearchView : BaseView<MainPresenter> {
        var isActive: Boolean

        fun search(query: String)
        fun showSearchResult(result: List<User>)
    }

    interface MyPageView : BaseView<MainPresenter> {
        var isActive: Boolean

        fun showMyPage()
    }

    interface TarekomiView : BaseView<MainPresenter> {
        fun vote()
    }
}