package com.example.chat.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chat.R
import com.example.chat.data.model.UserModel
import com.example.chat.databinding.UserSampleBinding

class AllUserAdapter(var userList:MutableList<UserModel>) : RecyclerView.Adapter<AllUserAdapter.AllUserViewHolder>() {
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
            "${userList[position].firstName} ${userList[position].lastName}"
        holder.sampleBinding.txtSampleFirstLetter.text =
            "${userList[position].firstName.first()}${userList[position].lastName.first()}"
        
    }
    @SuppressLint("NotifyDataSetChanged")
    fun dataSetChanged(l1: MutableList<UserModel>) {
        userList=l1
        notifyDataSetChanged()
    }
}