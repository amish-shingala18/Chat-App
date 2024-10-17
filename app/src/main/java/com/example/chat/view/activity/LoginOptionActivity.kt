package com.example.chat.view.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.chat.R
import com.example.chat.databinding.ActivityLoginOptionBinding

class LoginOptionActivity : AppCompatActivity() {
    private lateinit var binding:ActivityLoginOptionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginOptionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initClick()
    }
    private fun initClick() {
        binding.txtSignUp.setOnClickListener {
            startActivity(Intent(this@LoginOptionActivity,SignUpActivity::class.java))
        }
        binding.txtSignIn.setOnClickListener {
            startActivity(Intent(this@LoginOptionActivity,SignInActivity::class.java))
        }
    }
}