package com.example.chat.view.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chat.R
import com.example.chat.data.helper.AuthenticationHelper.Companion.authenticationHelper
import com.example.chat.data.model.ChatDocModel
import com.example.chat.databinding.UserSampleBinding
import com.example.chat.view.activity.ChatActivity

class UserAdapter(private var userList: MutableList<ChatDocModel>) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sampleBinding = UserSampleBinding.bind(itemView)
    }
    @SuppressLint("NotifyDataSetChanged")
    fun search(filterList: List <ChatDocModel>) {
        userList = filterList.toMutableList()
        notifyDataSetChanged()
    }
    @SuppressLint("NotifyDataSetChanged")
    fun dataSetChanged(l1: MutableList<ChatDocModel>) {
        userList = l1
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_sample, parent, false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        if (userList[position].uid1["uid"] != authenticationHelper.user!!.uid) {
            holder.sampleBinding.txtSampleName.text = userList[position].uid1["name"]?.substring(0,1)?.uppercase() + userList[position].uid1["name"]?.substring(1)?.lowercase()
            holder.sampleBinding.txtSampleFirstLetter.text =
                userList[position].uid1["name"]?.get(0).toString()
        } else {
            holder.sampleBinding.txtSampleName.text = userList[position].uid2["name"]?.substring(0,1)?.uppercase() + userList[position].uid2["name"]?.substring(1)?.lowercase()
            holder.sampleBinding.txtSampleFirstLetter.text =
                userList[position].uid2["name"]?.get(0).toString()
        }
        holder.sampleBinding.clUsers.setOnClickListener {
            if (userList[position].uid1["uid"] != authenticationHelper.user!!.uid) {

                val intent = Intent(holder.itemView.context, ChatActivity::class.java)
                intent.putExtra("uid", userList[position].uids[0])
                intent.putExtra("firstName", userList[position].uid1["name"])
                intent.putExtra("email", userList[position].uid1["email"])
                intent.putExtra("mobile", userList[position].uid1["mobile"])
                holder.itemView.context.startActivity(intent)
            } else{
                val intent = Intent(holder.itemView.context, ChatActivity::class.java)
                intent.putExtra("uid", userList[position].uids[1])
                intent.putExtra("firstName", userList[position].uid2["name"])
                intent.putExtra("email", userList[position].uid2["email"])
                intent.putExtra("mobile", userList[position].uid2["mobile"])
                holder.itemView.context.startActivity(intent)
            }
        }
    }
}