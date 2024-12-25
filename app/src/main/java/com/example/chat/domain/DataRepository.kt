package com.example.chat.domain

import com.example.chat.data.helper.AuthenticationHelper.Companion.authenticationHelper
import com.example.chat.data.helper.DBHelper.Companion.dbHelper
import com.example.chat.data.model.ChatDocModel
import com.example.chat.data.model.MessageModel
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
    fun readAllUsers(): Flow<MutableList<UserModel>> = dbHelper.readAllUsers()
    suspend fun sendMessage(clientId:String, model: MessageModel, docModel: ChatDocModel):Int = dbHelper.sendMessage(clientId,model,docModel)
    fun readMessage(uids:String): Flow<MutableList<MessageModel>> = dbHelper.readMessage(uids)
    fun checkChatUsers(): Flow<MutableList<ChatDocModel>> = dbHelper.checkChatUsers()
    fun deleteMessage(docId:String) = dbHelper.deleteMessage(docId)
}