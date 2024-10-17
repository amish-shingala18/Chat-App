package com.example.chat.view.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.chat.R
import com.example.chat.databinding.ActivitySignUpBinding
import com.example.chat.viewmodel.UserViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding:ActivitySignUpBinding
    private val userViewModel by viewModels<UserViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initClick()
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun initClick() {
        binding.btnSignUp.setOnClickListener {
            val signUpFN = binding.edtFN.text.toString()
            val signUpLN = binding.edtLN.text.toString()
            val signUpMN = binding.edtMN.text.toString()
            val signUpEmail = binding.edtSignUpEmail.text.toString()
            val signUpPassword = binding.edtSignUpPassword.text.toString()
            if(signUpFN.isEmpty()){
                binding.txtFNLayout.error="Please Enter First Name"
            } else if(signUpLN.isEmpty()){
                binding.txtLNLayout.error="Please Enter Last Name"
            } else if(signUpMN.isEmpty()){
                binding.txtMNLayout.error="Please Enter Mobile Number"
            } else if(signUpMN.length!=10){
                binding.txtMNLayout.error="Please Enter Valid Mobile Number"
            } else if(signUpEmail.isEmpty()){
                binding.txtSignUpEmailLayout.error="Please Enter Email"
            } else if(signUpPassword.isEmpty()){
                binding.txtSignUpPasswordLayout.error="Please Enter Password"
            } else{
                GlobalScope.launch {
                    Log.d("TAG", "initClick: ===========$signUpEmail =========$signUpPassword")
                    val signUpResult = userViewModel.getSignUp(signUpEmail, signUpPassword)
                    withContext(Dispatchers.Main) {
                        if (signUpResult == "Success") {
                            Toast.makeText(
                                this@SignUpActivity,
                                "Email Registered Successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                            finish()
                        } else {
                            Toast.makeText(this@SignUpActivity, signUpResult, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
        binding.txtAlreadyAcc.setOnClickListener {
            startActivity(Intent(this@SignUpActivity,SignInActivity::class.java))
        }
    }
}