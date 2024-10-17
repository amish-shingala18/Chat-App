package com.example.chat.data.helper

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.coroutines.tasks.await

class AuthenticationHelper {
    companion object{
        val authenticationHelper = AuthenticationHelper()
    }
    private val authentication = FirebaseAuth.getInstance()
    private var user = authentication.currentUser
    suspend fun signUp(email:String, password:String): String? {
        var msg: String? = null
        try {
            authentication.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
                msg = "Success"
            }.addOnFailureListener {
                msg = it.message
            }.await()
        }catch (e:FirebaseAuthException){
            msg = "Please enter valid Email Id or Password"
        }
        Log.d("TAG", "signUp: ======================================$msg")
        return msg
    }
    suspend fun signIn(email:String, password:String): String? {
        var msg: String? = null
        try {
            authentication.signInWithEmailAndPassword(email, password).addOnSuccessListener {
                msg = "Success"
            }.addOnFailureListener {
                msg = it.message
            }.await()
        }catch (e:FirebaseAuthException){
            msg = "Please enter valid Email Id or Password"
        }
        Log.d("TAG", "signUp: ======================================$msg")
        return msg
    }
    fun logout(){
        authentication.signOut()
    }
    fun checkUser()
    {
        user = authentication.currentUser
    }
}