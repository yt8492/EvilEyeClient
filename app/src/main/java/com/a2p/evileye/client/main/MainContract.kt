package com.a2p.evileye.client.main

import com.a2p.evileye.client.BasePresenter
import com.a2p.evileye.client.BaseView
import com.yt8492.evileye.protobuf.Tarekomi
import com.yt8492.evileye.protobuf.TarekomiSummary
import com.yt8492.evileye.protobuf.User

typealias ViewSwitchListener = (MainNavigationViewItem) -> Unit
typealias LogoutListener = () -> Unit

interface MainContract {
    interface MainPresenter : BasePresenter {
        fun tarekomi(url: String, userName: String, desc: String)
        fun showTarekomiBoardView()
        fun listTarekomiSummaries()
        fun showSearchView()
        fun search(query: String)
        fun listUser()
        fun vote(tarekomiId: Long, desc: String)
        fun showMyPageView()
        fun addViewSwitchListener(listener: ViewSwitchListener)
        fun setOnLogoutListener(listener: LogoutListener)
        fun logout()
    }

    interface TarekomiBoardView : BaseView<MainPresenter> {
        var isActive: Boolean

        fun showTarekomiSummaries(tarekomiSummaries: List<TarekomiSummary>)
        fun showTarekomiView()
        fun openTarekomiDetail(tarekomiSummary: TarekomiSummary)
    }

    interface TarekomiDetailView : BaseView<MainPresenter> {
        var waitingVote: Boolean
        fun vote()
        fun openUrl()
    }

    interface SearchView : BaseView<MainPresenter> {
        var isActive: Boolean

        fun search(query: String)
        fun showSearchResult(result: List<User>)
        fun openUserDetail(user: User)
    }

    interface MyPageView : BaseView<MainPresenter> {
        var isActive: Boolean

        fun showMyPage(userInfo: User)
        fun openTarekomiDetail(tarekomi: Tarekomi)
    }

    interface UserDetailView : BaseView<MainPresenter> {
        var isActive: Boolean

        fun openTarekomiDetail(tarekomi: Tarekomi)
    }
}