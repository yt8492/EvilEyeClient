package com.a2p.evileye.client.main.tarekomi_board

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager

import com.a2p.evileye.client.R
import com.a2p.evileye.client.main.MainContract
import com.a2p.evileye.client.main.tarekomi.TarekomiDialogFragment
import com.a2p.evileye.client.main.tarekomi_detail.TarekomiDetailFragment
import com.a2p.evileye.client.util.toast
import com.yt8492.evileye.protobuf.TarekomiSummary
import kotlinx.android.synthetic.main.activity_main.tarekomiFab
import kotlinx.android.synthetic.main.fragment_tarekomi_board.view.*

class TarekomiBoardFragment : Fragment(), MainContract.TarekomiBoardView {
    override var isActive = false
        get() = isVisible

    override lateinit var presenter: MainContract.MainPresenter

    private val listener: TarekomiSummaryItemClickListener = {
        openTarekomiDetail(it)
    }

    private val tarekomiSummaryRecyclerViewAdapter = TarekomiSummaryRecyclerViewAdapter(listener)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tarekomi_board, container, false)
        with(view.tarekomiSummariesRecyclerView) {
            layoutManager = LinearLayoutManager(inflater.context)
            adapter = tarekomiSummaryRecyclerViewAdapter
            addItemDecoration(DividerItemDecoration(inflater.context, DividerItemDecoration.VERTICAL))
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        initListener()
        presenter.listTarekomiSummaries()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            initListener()
        }
    }

    private fun initListener() {
        activity?.tarekomiFab?.setOnClickListener {
            showTarekomiView()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            TarekomiDialogFragment.REQUEST_CODE -> {
                if (resultCode == DialogInterface.BUTTON_POSITIVE && data != null) {
                    val url = data.getStringExtra(TarekomiDialogFragment.TAREKOMI_URL)
                    if (url.isNullOrBlank()) {
                        context?.toast("URLを入力してください")
                        showTarekomiView(url)
                        return
                    }
                    val userName = data.getStringExtra(TarekomiDialogFragment.TAREKOMI_USER)
                    if (userName.isNullOrBlank()) {
                        context?.toast("タレコミ対象のユーザ名を入力してください")
                        showTarekomiView(url)
                        return
                    }
                    val desc = data.getStringExtra(TarekomiDialogFragment.TAREKOMI_DESC)
                    presenter.tarekomi(url, userName, desc)
                    presenter.listTarekomiSummaries()
                }
            }
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun showTarekomiSummaries(tarekomiSummaries: List<TarekomiSummary>) {
        tarekomiSummaryRecyclerViewAdapter.initTarekomiSummaries(tarekomiSummaries)
    }

    override fun openTarekomiDetail(tarekomiSummary: TarekomiSummary) {
        val tarekomiDetailFragment =
            TarekomiDetailFragment.newInstance(tarekomiSummary.tarekomi, true)
        tarekomiDetailFragment.presenter = presenter
        childFragmentManager.commit {
            add(R.id.tarekomiBoardFrame, tarekomiDetailFragment)
            show(tarekomiDetailFragment)
        }
    }

    override fun showTarekomiView() {
        val voteDialog = TarekomiDialogFragment.newInstance().apply {
            setTargetFragment(this@TarekomiBoardFragment, TarekomiDialogFragment.REQUEST_CODE)
        }
        fragmentManager?.let {
            voteDialog.show(it, TarekomiDialogFragment.TAREKOMI_VIEW)
        }
    }

    override fun showTarekomiView(url: String) {
        val voteDialog = TarekomiDialogFragment.newInstance(url).apply {
            setTargetFragment(this@TarekomiBoardFragment, TarekomiDialogFragment.REQUEST_CODE)

        }
        fragmentManager?.let {
            voteDialog.show(it, TarekomiDialogFragment.TAREKOMI_VIEW)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = TarekomiBoardFragment()
    }
}
