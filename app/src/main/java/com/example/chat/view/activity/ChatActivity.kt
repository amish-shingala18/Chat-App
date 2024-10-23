package com.example.chat.view.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.chat.R
import com.example.chat.data.model.MessageModel
import com.example.chat.databinding.ActivityChatBinding
import com.example.chat.view.adapter.ChatAdapter
import com.example.chat.viewmodel.UserViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    private var clientId: String? = null
    var name: String? = null
    private var mobile: String? = null
    var email: String? = null
    private lateinit var chatAdapter: ChatAdapter
    private var chatList = mutableListOf<MessageModel>()

    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        val userViewModel by viewModels<UserViewModel>()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        getChatUserData()
        Log.d("TAG", "onCreate: -----------$clientId -------- $name ------ $email ------- $mobile")
        initClick()
        chatAdapter = ChatAdapter(chatList)
        binding.rvChats.adapter = chatAdapter
        Log.d("TAG", "onCreate:-------------=====================-----------------$clientId")
        readMessage(userViewModel)
        binding.imgSend.setOnClickListener {
            val messageText = binding.edtSendMessage.text.toString()
            val sdf = SimpleDateFormat("hh:mm")
            val currentTime = sdf.format(System.currentTimeMillis())
            if (messageText.isNotEmpty()) {
                userViewModel.sendMessage(
                    clientId!!,
                    messageText,
                    currentTime,
                    email!!,
                    name!!,
                    mobile!!
                )
                val messageModel = MessageModel(
                    clientId!!,
                    messageText,
                    currentTime
                )
                chatList.add(messageModel)
                binding.edtSendMessage.text.clear()
                GlobalScope.launch {
                    if (userViewModel.newChat == 0) {
                      readMessage(userViewModel)
                    }
                }
            }
        }
    }
    @OptIn(DelicateCoroutinesApi::class)
    private fun readMessage(userViewModel: UserViewModel){
        GlobalScope.launch {
            userViewModel.readMessage(clientId!!)
            withContext(Dispatchers.Main) {
                userViewModel.chatsList.collect {
                    Log.d("TAG", "onCreate:===========$it")
                    chatAdapter.dataChanged(it)
                }
            }
        }
    }
    @SuppressLint("SetTextI18n")
    fun getChatUserData() {
        binding.txtChatUserName.text = "${intent.getStringExtra("firstName")}"
        binding.txtChar.text = "${intent.getStringExtra("firstName")?.first()}"
        clientId = intent.getStringExtra("uid")
        name = intent.getStringExtra("firstName")
        email = intent.getStringExtra("email")
        mobile = intent.getStringExtra("mobile")
    }
    private fun initClick() {
        binding.imgBack.setOnClickListener {
            finish()
        }
    }
}