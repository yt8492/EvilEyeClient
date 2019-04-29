package com.a2p.evileye.client.main.user_detail


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
import com.a2p.evileye.client.main.user_tarekomi.TarekomiClickListener
import com.a2p.evileye.client.main.user_tarekomi.UserTarekomiRecyclerViewAdapter
import com.yt8492.evileye.protobuf.Tarekomi
import com.yt8492.evileye.protobuf.User
import kotlinx.android.synthetic.main.fragment_user_detail.view.*

class UserDetailFragment : Fragment(), MainContract.UserDetailView {
    override var isActive = false
        get() = isVisible

    override lateinit var presenter: MainContract.MainPresenter

    private val listener: TarekomiClickListener = {
        openTarekomiDetail(it)
    }

    private val userTarekomiRecyclerViewAdapter = UserTarekomiRecyclerViewAdapter(listener)

    private val user by lazy {
        User.parseFrom(arguments?.getByteArray(USER))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_user_detail, container, false)
        with(view) {
            userDetailUserNameTextView.text = user.userName
            with(userDetailTarekomiRecyclerView) {
                layoutManager = LinearLayoutManager(inflater.context)
                adapter = userTarekomiRecyclerViewAdapter
                addItemDecoration(DividerItemDecoration(inflater.context, DividerItemDecoration.VERTICAL))
            }
        }
        userTarekomiRecyclerViewAdapter.initTarekomis(user.tarekomisList)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback {
            fragmentManager?.commit {
                remove(this@UserDetailFragment)
            }
            true
        }
    }

    override fun openTarekomiDetail(tarekomi: Tarekomi) {

    }

    companion object {
        const val USER = "USER"

        @JvmStatic
        fun newInstance(user: User) =
            UserDetailFragment().apply {
                val  bundle = Bundle()
                bundle.putByteArray(USER, user.toByteArray())
                arguments  = bundle
            }
     }
}