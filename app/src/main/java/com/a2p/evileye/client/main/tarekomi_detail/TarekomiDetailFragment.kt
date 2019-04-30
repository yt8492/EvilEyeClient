package com.a2p.evileye.client.main.tarekomi_detail

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.commit

import com.a2p.evileye.client.R
import com.a2p.evileye.client.main.MainContract
import com.a2p.evileye.client.main.vote.VoteDialogFragment
import com.a2p.evileye.client.util.hideWayback
import com.yt8492.evileye.protobuf.Tarekomi
import kotlinx.android.synthetic.main.activity_main.tarekomiFab
import kotlinx.android.synthetic.main.fragment_tarekomi_detail.*
import kotlinx.android.synthetic.main.fragment_tarekomi_detail.view.*
import kotlin.math.absoluteValue

class TarekomiDetailFragment : Fragment(), MainContract.TarekomiDetailView {
    override lateinit var presenter: MainContract.MainPresenter
    override var waitingVote: Boolean
        get() = waiting
        set(value) = Unit

    private val waiting by lazy {
        arguments?.getBoolean(WAITING_VOTE) ?: false
    }

    private val tarekomi: Tarekomi by lazy {
        Tarekomi.parseFrom(arguments?.getByteArray(TAREKOMI))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tarekomi_detail, container, false)
        with(view) {
            val hash = tarekomi.targetUserName.hashCode().absoluteValue
            tarekomiDetailUserImageView.setImageResource(icons[hash % icons.size])
            tarekomiDetailUserNameTextView.text = tarekomi.targetUserName
            tarekomiDetailUrlTextView.text = tarekomi.url.hideWayback()
            tarekomiDetailDescTextView.text = tarekomi.desc
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        activity?.tarekomiFab?.let { fab ->
            fab.visibility = if (waiting) {
                fab.setOnClickListener {
                    vote()
                }
                View.VISIBLE
            } else {
                View.GONE
            }
        }
        activity?.onBackPressedDispatcher?.addCallback {
            fragmentManager?.commit {
                remove(this@TarekomiDetailFragment)
            }
            true
        }
        tarekomiDetailUrlTextView.setOnClickListener {
            openUrl()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        parentFragment?.onResume()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            VoteDialogFragment.REQUEST_CODE -> {
                if (resultCode == DialogInterface.BUTTON_POSITIVE) {
                    presenter.vote(tarekomi.id, data?.getStringExtra(VoteDialogFragment.VOTE_DESC) ?: "")
                }
            }
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun vote() {
        val voteDialog = VoteDialogFragment().apply {
            setTargetFragment(this@TarekomiDetailFragment, VoteDialogFragment.REQUEST_CODE)
        }
        fragmentManager?.let {
            voteDialog.show(it, VoteDialogFragment.VOTE_VIEW)
        }
    }

    override fun openUrl() {
        val url = tarekomi.url.toUri()
        val intent = Intent(Intent.ACTION_VIEW, url)
        startActivity(intent)
    }

    companion object {
        const val TAREKOMI = "TAREKOMI"
        const val WAITING_VOTE = "WAITING_VOTE"

        private val icons = arrayOf(R.drawable.user_icon_1, R.drawable.user_icon_2, R.drawable.user_icon_3)

        @JvmStatic
        fun newInstance(tarekomi: Tarekomi, waitingVote: Boolean) =
            TarekomiDetailFragment().apply {
                val bundle = Bundle()
                bundle.putByteArray(TAREKOMI, tarekomi.toByteArray())
                bundle.putBoolean(WAITING_VOTE, waitingVote)
                arguments = bundle
            }
    }
}
