package com.a2p.evileye.client.main.tarekomi_board


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager

import com.a2p.evileye.client.R
import com.a2p.evileye.client.main.MainContract
import com.a2p.evileye.client.util.toast
import com.yt8492.evileye.protobuf.TarekomiSummary
import kotlinx.android.synthetic.main.fragment_tarekomi_board.view.*

class TarekomiBoardFragment : Fragment(), MainContract.TarekomiBoardView {
    override var isActive = false
        get() = isVisible

    override lateinit var presenter: MainContract.MainPresenter

    private val listener: TarekomiSummaryItemClickListener = {
        context?.toast("clicked: ${it.tarekomi.desc}")
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.listTarekomiSummaries()
    }

    override fun showTarekomiView() {

    }

    override fun showTarekomiSummaries(tarekomiSummaries: List<TarekomiSummary>) {
        tarekomiSummaryRecyclerViewAdapter.addTarekomiSummaries(tarekomiSummaries)
    }

    companion object {
        @JvmStatic
        fun newInstance() = TarekomiBoardFragment()
    }
}
