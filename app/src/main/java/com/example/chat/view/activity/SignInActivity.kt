package com.example.chat.view.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.chat.MainActivity
import com.example.chat.R
import com.example.chat.databinding.ActivitySignInBinding

class SignInActivity : AppCompatActivity() {
    private lateinit var binding:ActivitySignInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initClick()
    }

    private fun initClick() {
        binding.btnSignIn.setOnClickListener {
            val signInEmail = binding.edtSignInEmail.text.toString()
            val signInPassword = binding.edtSignInPassword.text.toString()
            if(signInEmail.isEmpty()){
                binding.txtLoginEmailLayout.error="Please Enter Email"
            } else if(signInPassword.isEmpty()){
                binding.txtLoginPasswordLayout.error="Please Enter Password"
            } else{
                startActivity(Intent(this@SignInActivity,MainActivity::class.java))
            }
        }
        binding.txtNoAcc.setOnClickListener {
            startActivity(Intent(this@SignInActivity,SignUpActivity::class.java))
        }
    }
}