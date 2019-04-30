package com.a2p.evileye.client.main.tarekomi_board

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.a2p.evileye.client.R
import com.a2p.evileye.client.util.hideWayback
import com.yt8492.evileye.protobuf.TarekomiSummary
import kotlin.math.absoluteValue

typealias TarekomiSummaryItemClickListener = (TarekomiSummary) -> Unit

class TarekomiSummaryRecyclerViewAdapter(private val listener: TarekomiSummaryItemClickListener)
    : RecyclerView.Adapter<TarekomiSummaryViewHolder>() {

    private val tarekomiSummaries = mutableListOf<TarekomiSummary>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TarekomiSummaryViewHolder {
        val inflate = LayoutInflater.from(parent.context).inflate(R.layout.item_tarekomi_summary, parent, false)
        return TarekomiSummaryViewHolder(inflate)
    }

    override fun getItemCount(): Int {
        return tarekomiSummaries.size
    }

    override fun onBindViewHolder(holder: TarekomiSummaryViewHolder, position: Int) {
        val tarekomiSummary = tarekomiSummaries[position]
        with(holder) {
            val hash = tarekomiSummary.tarekomi.targetUserName.hashCode().absoluteValue
            userImageView.setImageResource(icons[hash % icons.size])
            userNameTextView.text = tarekomiSummary.tarekomi.targetUserName
            urlTextView.text = tarekomiSummary.tarekomi.url.hideWayback()
            descTextView.text = tarekomiSummary.tarekomi.desc
            itemView.setOnClickListener {
                listener(tarekomiSummary)
            }
        }
    }

    fun addTarekomiSummaries(tarekomiSummaries: List<TarekomiSummary>) {
        this.tarekomiSummaries.addAll(tarekomiSummaries)
        notifyDataSetChanged()
    }

    fun initTarekomiSummaries(tarekomiSummaries: List<TarekomiSummary>) {
        this.tarekomiSummaries.clear()
        this.tarekomiSummaries.addAll(tarekomiSummaries)
        notifyDataSetChanged()
    }

    companion object {
        private val icons = arrayOf(R.drawable.user_icon_1, R.drawable.user_icon_2, R.drawable.user_icon_3)
    }
}