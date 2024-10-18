package com.example.chat.view.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.chat.R
import com.example.chat.data.model.UserModel
import com.example.chat.databinding.ActivityAllUserBinding
import com.example.chat.view.adapter.AllUserAdapter
import com.example.chat.viewmodel.UserViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AllUserActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAllUserBinding
    private lateinit var allUserAdapter: AllUserAdapter
    private var allUserList= mutableListOf<UserModel>()
    private val userViewModel by viewModels<UserViewModel>()
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityAllUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        userViewModel.readAllUsers()
        allUserAdapter=AllUserAdapter(allUserList)
        binding.rvAllUsers.adapter = allUserAdapter
        GlobalScope.launch {
            withContext(Dispatchers.Main) {
                userViewModel.usersList.collect {
                    allUserAdapter.dataSetChanged(it)
                }
            }
        }
    }
}