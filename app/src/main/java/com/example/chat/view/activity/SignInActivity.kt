package com.example.chat.view.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.chat.R
import com.example.chat.data.helper.AuthenticationHelper.Companion.authenticationHelper
import com.example.chat.data.helper.DBHelper.Companion.dbHelper
import com.example.chat.data.model.UserModel
import com.example.chat.databinding.ActivitySignInBinding
import com.example.chat.viewmodel.UserViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignInActivity : AppCompatActivity() {
    private lateinit var googleClient: GoogleSignInClient
    private lateinit var binding:ActivitySignInBinding
    private val userViewModel by viewModels<UserViewModel>()
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
        googleSignIn()
        val registerGoogle = registerForActivityResult(ActivityResultContracts.StartActivityForResult(),{
            val googleId = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            val credential = GoogleAuthProvider.getCredential(googleId.result.idToken,null)
            FirebaseAuth.getInstance().signInWithCredential(credential).addOnSuccessListener {
                authenticationHelper.checkUser()
                val intent = Intent(this@SignInActivity, ProfileActivity::class.java)
                intent.putExtra("email",googleId.result.email)
                startActivity(intent)
                finish()
            }.addOnFailureListener{
                Toast.makeText(this@SignInActivity, "something went wrong!!", Toast.LENGTH_SHORT).show()
            }
        })
        binding.lnrGoogleSignIn.setOnClickListener {
            val intent = googleClient.signInIntent
            registerGoogle.launch(intent)
        }
    }
    private fun googleSignIn(){
        val googleSignInOption = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.token_id))
            .requestEmail()
            .build()
        googleClient = GoogleSignIn.getClient(this@SignInActivity,googleSignInOption)
    }
    @OptIn(DelicateCoroutinesApi::class)
    private fun initClick() {
        binding.btnSignIn.setOnClickListener {
            val signInEmail = binding.edtSignInEmail.text.toString()
            val signInPassword = binding.edtSignInPassword.text.toString()
            if(signInEmail.isEmpty()){
                binding.txtLoginEmailLayout.error="Please Enter Email"
            } else if(signInPassword.isEmpty()){
                binding.txtLoginPasswordLayout.error="Please Enter Password"
            } else{
                GlobalScope.launch {
                    Log.d("TAG", "initClick: ===========$signInEmail =========$signInPassword")
                    val signInResult = userViewModel.getSignIn(signInEmail,signInPassword)
                    withContext(Dispatchers.Main) {
                        Log.d("TAG", "initClick: ============$signInResult")
                        if (signInResult == "Success") {
                            Toast.makeText(
                                this@SignInActivity,
                                "Signed In Successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                            authenticationHelper.checkUser()
                            val intent = Intent(this@SignInActivity, ProfileActivity::class.java)
                            intent.putExtra("email",signInEmail)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this@SignInActivity, signInResult, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
        binding.txtNoAcc.setOnClickListener {
            startActivity(Intent(this@SignInActivity,SignUpActivity::class.java))
        }
    }
}