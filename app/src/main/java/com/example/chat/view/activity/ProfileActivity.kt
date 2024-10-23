package com.example.chat.view.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.chat.MainActivity
import com.example.chat.R
import com.example.chat.data.helper.AuthenticationHelper.Companion.authenticationHelper
import com.example.chat.data.helper.DBHelper.Companion.dbHelper
import com.example.chat.data.model.UserModel
import com.example.chat.databinding.ActivityProfileBinding
import com.example.chat.viewmodel.UserViewModel

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding:ActivityProfileBinding
    private val userViewModel by viewModels<UserViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val email = intent.getStringExtra("email")
        Log.d("TAG", "onCreate: ProfileActivity====================== $email ")
        binding.edtProfileEmail.setText(email)
        userViewModel.readUserData()
        userViewModel.liveModel.observe(this) {
            if (it.firstName==null) {
                binding.edtProfileFN.setText("")
            } else if(it.lastName==null){
                binding.edtProfileLN.setText("")
            }else if(it.mobile==null){
                binding.edtProfileMN.setText("")
            }
            else {
                binding.edtProfileFN.setText(it.firstName)
                binding.edtProfileLN.setText(it.lastName)
                binding.edtProfileMN.setText(it.mobile)
            }
        }
        initClick()
    }
    private fun initClick(){
        binding.btnSubmitProfile.setOnClickListener {
            val fn = binding.edtProfileFN.text.toString()
            val ln = binding.edtProfileLN.text.toString()
            val mn = binding.edtProfileMN.text.toString()
            val email = binding.edtProfileEmail.text.toString()
            if(fn.isEmpty()){
                binding.txtFNProfileLayout.error="Please enter first name"
            }else if(ln.isEmpty()){
                binding.txtLNProfileLayout.error="Please enter last name"
            }else if(mn.isEmpty()){
                binding.txtMNProfileLayout.error="Please enter mobile number"
            }else if(mn.length!=10){
                binding.txtMNProfileLayout.error="Please enter valid mobile number"
            }else if(email.isEmpty()){
                binding.txtEmailProfileLayout.error="Please enter email address"
            }else{
                dbHelper.insertUserData(UserModel(firstName = fn,
                    lastName = ln,
                    mobile = mn,
                    email = email,
                    uid = authenticationHelper.user!!.uid))
                authenticationHelper.checkUser()
                startActivity(Intent(this@ProfileActivity, MainActivity::class.java))
                finish()
            }
        }
    }
}