package com.example.chat.view.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chat.R
import com.example.chat.data.model.UserModel
import com.example.chat.databinding.UserSampleBinding
import com.example.chat.view.activity.ChatActivity

class UserAdapter(list: MutableList<UserModel>) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sampleBinding = UserSampleBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_sample,parent,false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.sampleBinding.clUsers.setOnClickListener {
            holder.itemView.context.startActivity(Intent(holder.itemView.context,ChatActivity::class.java))
        }
    }
}