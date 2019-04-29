package com.a2p.evileye.client.main.my_page


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager

import com.a2p.evileye.client.R
import com.a2p.evileye.client.main.MainContract
import com.a2p.evileye.client.main.tarekomi_detail.TarekomiDetailFragment
import com.a2p.evileye.client.main.user_tarekomi.TarekomiClickListener
import com.a2p.evileye.client.main.user_tarekomi.UserTarekomiRecyclerViewAdapter
import com.yt8492.evileye.protobuf.Tarekomi
import com.yt8492.evileye.protobuf.User
import kotlinx.android.synthetic.main.fragment_my_page.*
import kotlinx.android.synthetic.main.fragment_my_page.view.*

class MyPageFragment : Fragment(), MainContract.MyPageView {
    override var isActive = false
        get() = isVisible

    override lateinit var presenter: MainContract.MainPresenter

    private val listener: TarekomiClickListener = {
        openTarekomiDetail(it)
    }

    private val userTarekomiRecyclerViewAdapter = UserTarekomiRecyclerViewAdapter(listener)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_my_page, container, false)
        with(view.myPageTarekomiRecyclerView) {
            layoutManager = LinearLayoutManager(inflater.context)
            adapter = userTarekomiRecyclerViewAdapter
            addItemDecoration(DividerItemDecoration(inflater.context, DividerItemDecoration.VERTICAL))
        }
        return view
    }

    override fun showMyPage(userInfo: User) {
        myPageUserNameTextView.text = userInfo.userName
        myPageLogoutButton.setOnClickListener {
            presenter.logout()
        }
        userTarekomiRecyclerViewAdapter.initTarekomis(userInfo.tarekomisList)
    }

    override fun openTarekomiDetail(tarekomi: Tarekomi) {
        val tarekomiDetailFragment = TarekomiDetailFragment.newInstance(tarekomi, false)
        tarekomiDetailFragment.presenter = presenter
        childFragmentManager.commit {
            add(R.id.myPageFrame, tarekomiDetailFragment)
            show(tarekomiDetailFragment)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = MyPageFragment()
    }
}
