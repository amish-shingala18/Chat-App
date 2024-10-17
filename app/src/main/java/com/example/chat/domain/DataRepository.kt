package com.example.chat.domain

import com.example.chat.data.helper.AuthenticationHelper.Companion.authenticationHelper

class DataRepository {
    companion object{
        val repository = DataRepository()
    }
    suspend fun getSignUp(repoEmail:String,repoPassword:String)
    = authenticationHelper.signUp(repoEmail,repoPassword)
}