package com.a2p.evileye.client.login


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import com.a2p.evileye.client.R
import com.a2p.evileye.client.main.MainActivity
import com.a2p.evileye.client.util.toast
import kotlinx.android.synthetic.main.fragment_login.*

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
    }

    private fun initListener() {
        loginButton.setOnClickListener {
            val userName = loginUserNameEditText.text.toString()
            val password = loginPasswordEditText.text.toString()
            presenter.login(userName, password)
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun openVoteList() {
        context?.let {
            val intent = MainActivity.createIntent(it)
            startActivity(intent)
        }
    }

    override fun showLoginFailure() {
        context?.toast("ログインに失敗しました")
    }

    override fun showConnectionFailure() {
        context?.toast("サーバーとの通信に失敗しました")
    }

    companion object {
        const val TAG = "Login Fragment"

        @JvmStatic
        fun newInstance() = LoginFragment()
    }
}
