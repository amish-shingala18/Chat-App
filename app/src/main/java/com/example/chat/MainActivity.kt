package com.example.chat

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.chat.data.helper.AuthenticationHelper.Companion.authenticationHelper
import com.example.chat.data.model.UserModel
import com.example.chat.databinding.ActivityMainBinding
import com.example.chat.view.activity.AllUserActivity
import com.example.chat.view.activity.LoginOptionActivity
import com.example.chat.view.adapter.UserAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var userAdapter: UserAdapter
    private val userList = mutableListOf<UserModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        userAdapter=UserAdapter(userList)
        binding.rvMainUsers.adapter=userAdapter
        initClick()
    }

    private fun initClick() {
        binding.imgMenu.setOnClickListener {
            authenticationHelper.logout()
            startActivity(Intent(this@MainActivity, LoginOptionActivity::class.java))
        }
        binding.fabAllUsers.setOnClickListener {
            startActivity(Intent(this@MainActivity,AllUserActivity::class.java))
        }
    }

}