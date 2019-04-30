package com.a2p.evileye.client.main.user_tarekomi

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.a2p.evileye.client.R
import com.a2p.evileye.client.util.hideWayback
import com.yt8492.evileye.protobuf.Tarekomi

typealias TarekomiClickListener = (Tarekomi) -> Unit

class UserTarekomiRecyclerViewAdapter(private val listener: TarekomiClickListener)
    : RecyclerView.Adapter<UserTarekomiViewHolder>() {
    private val tarekomis = mutableListOf<Tarekomi>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserTarekomiViewHolder {
        val inflate = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tarekomi, parent, false)
        return UserTarekomiViewHolder(inflate)
    }

    override fun getItemCount(): Int {
        return tarekomis.size
    }

    override fun onBindViewHolder(holder: UserTarekomiViewHolder, position: Int) {
        val tarekomi = tarekomis[position]
        with(holder) {
            tarekomiUrlTextView.text = tarekomi.url.hideWayback()
            tarekomiDescTextView.text = tarekomi.desc
            itemView.setOnClickListener {
                listener(tarekomi)
            }
        }
    }

    fun addTarekomis(tarekomis: List<Tarekomi>) {
        this.tarekomis.addAll(tarekomis)
        notifyDataSetChanged()
    }

    fun initTarekomis(tarekomis: List<Tarekomi>) {
        this.tarekomis.clear()
        this.tarekomis.addAll(tarekomis)
        notifyDataSetChanged()
    }
}