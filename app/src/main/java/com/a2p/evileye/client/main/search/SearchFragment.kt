package com.a2p.evileye.client.main.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.a2p.evileye.client.R
import com.a2p.evileye.client.main.MainContract
import com.yt8492.evileye.protobuf.User

class SearchFragment : Fragment(), MainContract.SearchView {
    override var isActive = false
        get() = isVisible

    override lateinit var presenter: MainContract.MainPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun search(query: String) {

    }

    override fun showSearchResult(result: List<User>) {

    }

    companion object {
        @JvmStatic
        fun newInstance() = SearchFragment()
    }
}
