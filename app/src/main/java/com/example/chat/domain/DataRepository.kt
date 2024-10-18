package com.example.chat.domain

import com.example.chat.data.helper.AuthenticationHelper.Companion.authenticationHelper
import com.example.chat.data.helper.DBHelper.Companion.dbHelper
import com.example.chat.data.model.UserModel
import kotlinx.coroutines.flow.Flow

class DataRepository {
    companion object{
        val repository = DataRepository()
    }
    suspend fun getSignUp(repoEmail:String,repoPassword:String) = authenticationHelper.signUp(repoEmail,repoPassword)
    suspend fun getSignIn(repoEmail:String,repoPassword:String) = authenticationHelper.signIn(repoEmail,repoPassword)
    fun insertUserData(userModel: UserModel)=dbHelper.insertUserData(userModel)
    suspend fun readUserData()= dbHelper.readUserData()
    suspend fun readAllUsers(): Flow<MutableList<UserModel>> = dbHelper.readAllUsers()
}