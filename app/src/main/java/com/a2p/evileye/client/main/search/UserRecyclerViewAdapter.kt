package com.a2p.evileye.client.main.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.a2p.evileye.client.R
import com.yt8492.evileye.protobuf.User

typealias UserItemClickListerner = (User) -> Unit

class UserRecyclerViewAdapter(private val listener: UserItemClickListerner) : RecyclerView.Adapter<UserViewHolder>() {
    private val users = mutableListOf<User>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflate = LayoutInflater.from(parent.context).inflate(R.layout.item_user, null, false)
        return UserViewHolder(inflate)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user =  users[position]
        with(holder) {
            userNameTextView.text = user.userName
            itemView.setOnClickListener {
                listener(user)
            }
        }
    }

    fun addUsers(users: List<User>) {
        this.users.addAll(users)
        notifyDataSetChanged()
    }

    fun initUsers(users: List<User>) {
        this.users.clear()
        this.users.addAll(users)
        notifyDataSetChanged()
    }
}