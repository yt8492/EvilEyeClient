package com.a2p.evileye.client.main


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.a2p.evileye.client.R
import com.yt8492.evileye.protobuf.Tarekomi

class TarekomiBoardFragment : Fragment(), MainContract.TarekomiBoardView {
    override var isActive = false
        get() = isVisible

    override lateinit var presenter: MainContract.MainPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tarekomi_board, container, false)
    }

    override fun showTarekomiView() {

    }

    override fun showTarekomiList(tarekomis: List<Tarekomi>) {

    }

    companion object {
        @JvmStatic
        fun newInstance() = TarekomiBoardFragment()
    }
}
