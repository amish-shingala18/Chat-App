package com.example.chat

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.PopupMenu
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.chat.data.helper.AuthenticationHelper.Companion.authenticationHelper
import com.example.chat.data.model.ChatDocModel
import com.example.chat.databinding.ActivityMainBinding
import com.example.chat.view.activity.AllUserActivity
import com.example.chat.view.activity.LoginOptionActivity
import com.example.chat.view.activity.ProfileActivity
import com.example.chat.view.adapter.UserAdapter
import com.example.chat.view.fragment.NetworkFragment
import com.example.chat.viewmodel.UserViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private var filterList= mutableListOf<ChatDocModel>()
    private lateinit var binding:ActivityMainBinding
    private lateinit var userAdapter: UserAdapter
    private var userList= mutableListOf<ChatDocModel>()
    private val userViewModel by viewModels<UserViewModel>()
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
//        (application as ChatNetwork).liveData.observe(this) {
//            val networkFragment = NetworkFragment()
//            if (!it) {
//                networkFragment.isCancelable = false
//                networkFragment.show(supportFragmentManager, "network")
//            }else{
//                networkFragment.dismiss()
//            }
//        }
        userAdapter = UserAdapter(userList)
        binding.rvMainUsers.adapter = userAdapter
        initClick()
        GlobalScope.launch {
            withContext(Dispatchers.Main) {
                userViewModel.checkChatUsers().collect{
                    userAdapter.dataSetChanged(it)
                }
            }
        }
    }
    private fun initClick() {
        binding.imgMenu.setOnClickListener {
            val popupMenu = PopupMenu(this@MainActivity,binding.imgMenu)
            popupMenu.menuInflater.inflate(R.menu.pop_up_menu,popupMenu.menu)
            popupMenu.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.menu_sign_out->{
                        authenticationHelper.logout()
                        startActivity(Intent(this@MainActivity, LoginOptionActivity::class.java))
                        true
                    }
                    R.id.menu_profile->{
                        val intent = Intent(this@MainActivity, ProfileActivity::class.java)
                        val getEmail = authenticationHelper.user!!.email
                        Log.d("TAG", "initClick: ========================$getEmail")
                        intent.putExtra("email",getEmail)
                        startActivity(intent)
                        true
                    }
                    else -> true
                }
            }
            popupMenu.show()
        }
        binding.fabAllUsers.setOnClickListener {
            startActivity(Intent(this@MainActivity,AllUserActivity::class.java))
        }
        binding.svChatUsers.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                filterList = userList.filter { chatDocModel ->
                    val name1 = chatDocModel.uid1["name"]?.toString()?.lowercase() ?: ""
                    val name2 = chatDocModel.uid2["name"]?.toString()?.lowercase() ?: ""
                    name1.contains(newText!!.lowercase(Locale.getDefault())) ||
                            name2.contains(newText.lowercase(Locale.getDefault()))
                }.toMutableList()
                userAdapter.search(filterList)
                return true
            }
        })
    }
}