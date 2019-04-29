package com.a2p.evileye.client.main.search

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
import com.a2p.evileye.client.main.user_detail.UserDetailFragment
import com.yt8492.evileye.protobuf.User
import kotlinx.android.synthetic.main.fragment_search.view.*

class SearchFragment : Fragment(), MainContract.SearchView {
    override var isActive = false
        get() = isVisible

    override lateinit var presenter: MainContract.MainPresenter

    private val listener: UserItemClickListerner = {
        openUserDetail(it)
    }

    private val userRecyclerViewAdapter = UserRecyclerViewAdapter(listener)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        with(view.userRecyclerView) {
            layoutManager = LinearLayoutManager(inflater.context)
            adapter =  userRecyclerViewAdapter
            addItemDecoration(DividerItemDecoration(inflater.context, DividerItemDecoration.VERTICAL))
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.listUser()
    }

    override fun search(query: String) {

    }

    override fun showSearchResult(result: List<User>) {
        userRecyclerViewAdapter.initUsers(result)
    }

    override fun openUserDetail(user: User) {
        val userDetailFragment = UserDetailFragment.newInstance(user)
        userDetailFragment.presenter = presenter
        childFragmentManager.commit {
            add(R.id.searchFrame, userDetailFragment)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = SearchFragment()
    }
}
