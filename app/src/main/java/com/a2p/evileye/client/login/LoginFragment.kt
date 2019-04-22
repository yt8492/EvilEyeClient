package com.a2p.evileye.client.login


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment

import com.a2p.evileye.client.R

class LoginFragment : Fragment(), LoginContract.View {
    override var isActive: Boolean = false
        get() = isAdded

    override lateinit var presenter: LoginContract.Presenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun openVoteList() {
        Toast.makeText(context, "Connected", Toast.LENGTH_SHORT).show()
    }

    companion object {
        @JvmStatic
        fun newInstance() = LoginFragment()
    }
}
