package com.a2p.evileye.client.main.tarekomi_detail

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit

import com.a2p.evileye.client.R
import com.a2p.evileye.client.main.MainContract
import com.a2p.evileye.client.main.vote.VoteDialogFragment
import com.yt8492.evileye.protobuf.TarekomiSummary
import kotlinx.android.synthetic.main.activity_main.tarekomiFab
import kotlinx.android.synthetic.main.fragment_tarekomi_detail.view.*

class TarekomiDetailFragment : Fragment(), MainContract.TarekomiDetailView {
    override lateinit var presenter: MainContract.MainPresenter
    override var waitingVote: Boolean
        get() = waiting
        set(value) = Unit

    private val waiting by lazy {
        arguments?.getBoolean(WAITING_VOTE) ?: false
    }

    private val tarekomiSummary by lazy {
        TarekomiSummary.parseFrom(arguments?.getByteArray(TAREKOMI_SUMMARY))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tarekomi_detail, container, false)
        with(view) {
            tarekomiDetailUserNameTextView.text = tarekomiSummary.userName
            tarekomiDetailUrlTextView.text = tarekomiSummary.tarekomi.url
            tarekomiDetailDescTextView.text = tarekomiSummary.tarekomi.desc
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.tarekomiFab?.let { fab ->
            fab.visibility = if (waiting) {
                fab.setOnClickListener {
                    val voteDialog = VoteDialogFragment().apply {
                        setTargetFragment(this@TarekomiDetailFragment, VoteDialogFragment.REQUEST_CODE)
                    }
                    fragmentManager?.let {
                        voteDialog.show(it, VoteDialogFragment.VOTE_VIEW)
                    }
                }
                View.VISIBLE
            } else {
                View.GONE
            }
        }
        activity?.onBackPressedDispatcher?.addCallback {
            fragmentManager?.commit {
                this.remove(this@TarekomiDetailFragment)
            }
            true
        }
    }

    override fun onDestroy() {
        activity?.tarekomiFab?.setOnClickListener(null)
        super.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            VoteDialogFragment.REQUEST_CODE -> {
                if (resultCode == DialogInterface.BUTTON_POSITIVE) {
                    presenter.vote(tarekomiSummary.tarekomi.id, data?.getStringExtra(VoteDialogFragment.VOTE_DESC) ?: "")
                }
            }
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun vote() {

    }

    override fun openUrl() {

    }

    companion object {
        const val TAREKOMI_SUMMARY = "TAREKOMI_SUMMARY"
        const val WAITING_VOTE = "WAITING_VOTE"

        @JvmStatic
        fun newInstance(tarekomiSummary: TarekomiSummary, waitingVote: Boolean) =
            TarekomiDetailFragment().apply {
                val bundle = Bundle()
                bundle.putByteArray(TAREKOMI_SUMMARY, tarekomiSummary.toByteArray())
                bundle.putBoolean(WAITING_VOTE, waitingVote)
                arguments = bundle
            }
    }
}
