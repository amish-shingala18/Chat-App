package com.example.chat.view.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chat.R
import com.example.chat.databinding.ChatSampleBinding

class ChatAdapter : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {
    class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sampleBinding = ChatSampleBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.chat_sample,parent,false)
        return ChatViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {

    }
}