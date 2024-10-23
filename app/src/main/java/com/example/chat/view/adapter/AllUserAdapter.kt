package com.example.chat.view.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chat.R
import com.example.chat.data.model.UserModel
import com.example.chat.databinding.UserSampleBinding
import com.example.chat.view.activity.ChatActivity

class AllUserAdapter(private var userList:MutableList<UserModel>):
    RecyclerView.Adapter<AllUserAdapter.AllUserViewHolder>() {
    class AllUserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sampleBinding = UserSampleBinding.bind(itemView)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllUserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_sample,parent,false)
        return AllUserViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userList.size
    }
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: AllUserViewHolder, position: Int) {
        holder.sampleBinding.txtSampleLastChat.visibility=View.GONE
        holder.sampleBinding.txtSampleTime.visibility=View.GONE
        holder.sampleBinding.txtSampleName.text=userList[position].firstName
        holder.sampleBinding.txtSampleName.text =
            "${userList[position].firstName.substring(0, 1).uppercase() + userList[position].firstName.substring(1).lowercase()} " +
                    (userList[position].lastName.substring(0, 1).uppercase() + userList[position].lastName.substring(1).lowercase())
        holder.sampleBinding.txtSampleFirstLetter.text =
            "${userList[position].firstName.first()}${userList[position].lastName.first()}"
        holder.sampleBinding.clUsers.setOnClickListener {
            val intent = Intent(holder.itemView.context, ChatActivity::class.java)
            intent.putExtra("firstName",userList[position].firstName)
            //intent.putExtra("lastName",userList[position].lastName)
            intent.putExtra("email",userList[position].email)
            intent.putExtra("mobile",userList[position].mobile)
            intent.putExtra("uid",userList[position].uid)
            holder.itemView.context.startActivity(intent)
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun dataSetChanged(l1: MutableList<UserModel>) {
        userList=l1
        notifyDataSetChanged()
    }
}