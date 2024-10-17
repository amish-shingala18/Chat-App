package com.example.chat.view.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.chat.MainActivity
import com.example.chat.R
import com.example.chat.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding:ActivityProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    private fun getProfileData(){

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
                //Insert User Data to Firebase
                startActivity(Intent(this@ProfileActivity, MainActivity::class.java))
            }
        }
    }
}