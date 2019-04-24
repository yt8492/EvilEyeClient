package com.a2p.evileye.client.main.tarekomi_board

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_tarekomi_summary.view.*

class TarekomiSummaryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val userImageView = view.tarekomiSummaryUserImageView
    val userNameTextView = view.tarekomiSummaryUserNameTextView
    val urlTextView = view.tarekomiSummaryUrlTextView
    val descTextView = view.tarekomiSummaryDescTextView
}