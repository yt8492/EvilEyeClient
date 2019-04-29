package com.a2p.evileye.client.main.search

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_user.view.*

class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val userImageView = view.usersUserImageView
    val userNameTextView = view.usersUserNameTextView
}