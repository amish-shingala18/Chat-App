package com.example.chat.view.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.chat.R
import com.example.chat.data.helper.DBHelper.Companion.dbHelper
import com.example.chat.data.model.MessageModel
import com.example.chat.databinding.ActivityChatBinding
import com.example.chat.view.adapter.ChatActionInterFace
import com.example.chat.view.adapter.ChatAdapter
import com.example.chat.viewmodel.UserViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat

class ChatActivity : AppCompatActivity() {
    private var status: Boolean = true
    private lateinit var binding: ActivityChatBinding
    private var clientId: String? = null
    var name: String? = null
    private var mobile: String? = null
    var email: String? = null
    private lateinit var chatAdapter: ChatAdapter
    private var chatList = mutableListOf<MessageModel>()

    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("SimpleDateFormat", "SetTextI18n")
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
        keyboardStatus()
        initAdapter(userViewModel)
        Log.d("TAG", "onCreate:-------------=====================-----------------$clientId")

        readMessage(userViewModel)

        binding.imgSend.setOnClickListener {
            val messageText = binding.edtSendMessage.text.toString()
            Log.d("TAG", "Sending Message: ============$messageText ")
            val sdf = SimpleDateFormat("dd/MM/yy HH:mm:ss")
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
                    currentTime,
                    docId = clientId!!
                )
                Log.d("TAG", "ChatActivity-=-=-=-=-===--====-==--==-==-=$messageModel")
                chatList.add(messageModel)
                binding.edtSendMessage.text.clear()
//                GlobalScope.launch {
//                    if (userViewModel.newChat == 0) {
//                      readMessage(userViewModel)
//                    }
//                }
            }
            if (chatList.isEmpty()) {
                GlobalScope.launch {
                    readMessage(userViewModel)
                }
            }
        }
    }

    private fun initAdapter(userViewModel: UserViewModel) {
        val chatAction = object : ChatActionInterFace {
            override fun deleteMessage(docId: String) {
                userViewModel.deleteMessage(docId)
            }
        }
        chatAdapter = ChatAdapter(chatList, chatAction)
        binding.rvChats.adapter = chatAdapter
    }

    @SuppressLint("SetTextI18n")
    @OptIn(DelicateCoroutinesApi::class)
    private fun keyboardStatus() {
        binding.main.getViewTreeObserver()
            .addOnGlobalLayoutListener {
                val heightDiff: Int =
                    binding.main.getRootView().height - binding.main.height
                if (heightDiff > dpToPx1(200f)) {
                    GlobalScope.launch {
                        dbHelper.changeStatus(status, 2)
                        withContext(Dispatchers.Main) {
                            binding.txtTypeOrOnline.text = "typing..."
                            binding.txtTypeOrOnline.visibility = View.VISIBLE
                        }
                    }
                } else {
                    binding.txtTypeOrOnline.text = " "
                    binding.txtTypeOrOnline.visibility = View.GONE
                }
            }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun readMessage(userViewModel: UserViewModel) {
        GlobalScope.launch {
            userViewModel.readMessage(clientId!!)
            withContext(Dispatchers.Main) {
                userViewModel.chatsList.collect {
                    Log.d("TAG", "onCreate:===========$it")
                    chatAdapter.dataChanged(it)
                    binding.rvChats.scrollToPosition(it.size - 1)
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
        status = intent.getBooleanExtra("status", false)
    }

    private fun initClick() {
        binding.imgBack.setOnClickListener {
            finish()
        }
    }

    private fun dpToPx1(valueInDp: Float): Float {
        val metrics: DisplayMetrics = getResources().displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, metrics)
    }
}