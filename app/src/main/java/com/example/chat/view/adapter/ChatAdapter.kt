package com.example.chat.view.adapter


import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chat.R
import com.example.chat.data.helper.AuthenticationHelper.Companion.authenticationHelper
import com.example.chat.data.model.MessageModel
import com.example.chat.databinding.ChatSampleBinding

class ChatAdapter(private var list: List<MessageModel>,val chatAction: ChatActionInterFace) : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {
    class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sampleBinding = ChatSampleBinding.bind(itemView)
    }
    @SuppressLint("NotifyDataSetChanged")
    fun dataChanged(l1: List<MessageModel>){
        list = l1
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.chat_sample,parent,false)
        return ChatViewHolder(view)
    }
    override fun getItemCount(): Int {
        return list.size
    }
    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.sampleBinding.txtSampleMessage.text = list[position].msg
        holder.sampleBinding.txtSampleChatTime.text = list[position].dateTime
        if(list[position].senderUid==authenticationHelper.user!!.uid){
            holder.sampleBinding.lnrChat.gravity=Gravity.END
            holder.sampleBinding.txtSampleMessage.setTextColor(android.graphics.Color.WHITE)
            holder.sampleBinding.lnrBgChats.setBackgroundResource(R.drawable.user_send)
            holder.sampleBinding.txtSampleChatTime.setTextColor(android.graphics.Color.WHITE)
            val params = holder.sampleBinding.lnrBgChats.layoutParams as ViewGroup.MarginLayoutParams
            params.setMargins(108, 0, 0, 0)
            holder.sampleBinding.lnrBgChats.layoutParams = params
        }else{
            holder.sampleBinding.lnrChat.gravity=Gravity.START
            holder.sampleBinding.lnrBgChats.setBackgroundResource(R.drawable.client_send)
            holder.sampleBinding.txtSampleMessage.setTextColor(android.graphics.Color.BLACK)
            holder.sampleBinding.txtSampleChatTime.setTextColor(android.graphics.Color.BLACK)
            val params = holder.sampleBinding.lnrBgChats.layoutParams as ViewGroup.MarginLayoutParams
            params.setMargins(0, 0, 108, 0)
            holder.sampleBinding.lnrBgChats.layoutParams = params
        }
        holder.sampleBinding.lnrChat.setOnLongClickListener {
            val dialog = Dialog(holder.sampleBinding.lnrChat.context)
            dialog.setContentView(R.layout.delete_msg)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
            dialog.show()
            dialog.findViewById<View>(R.id.btnDeleteMessage).setOnClickListener {
                chatAction.deleteMessage(list[position].docId)
                dialog.dismiss()
            }
            dialog.findViewById<View>(R.id.btnCancel).setOnClickListener {
                dialog.dismiss()
            }
            true
        }
    }
}
interface ChatActionInterFace {
    fun deleteMessage(docId:String)
}
