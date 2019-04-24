package com.a2p.evileye.client.main


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.a2p.evileye.client.R

class MyPageFragment : Fragment(), MainContract.MyPageView {
    override var isActive = false
        get() = isVisible

    override lateinit var presenter: MainContract.MainPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_page, container, false)
    }

    override fun showMyPage() {

    }

    companion object {
        @JvmStatic
        fun newInstance() = MyPageFragment()
    }
}
