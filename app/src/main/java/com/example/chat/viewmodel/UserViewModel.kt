package com.example.chat.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chat.data.helper.AuthenticationHelper.Companion.authenticationHelper
import com.example.chat.data.model.ChatDocModel
import com.example.chat.data.model.MessageModel
import com.example.chat.data.model.UserModel
import com.example.chat.domain.DataRepository.Companion.repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class UserViewModel: ViewModel() {
    var newChat = -1;
    private var mutableLiveModel = MutableLiveData<UserModel>()
    var liveModel: LiveData<UserModel> = mutableLiveModel
    var usersList: Flow<MutableList<UserModel>> = MutableStateFlow(mutableListOf())
    var chatsList : Flow<MutableList<MessageModel>> = MutableStateFlow(mutableListOf())
    private var checkUserLists : Flow<MutableList<ChatDocModel>> = MutableStateFlow(mutableListOf())
    suspend fun getSignUp(signUpEmail:String, signUpPassword:String): String? {
        val signUpResult: String? = repository.getSignUp(signUpEmail,signUpPassword)
        Log.e("TAG", "getSignUp: ViewModel=========================== $signUpResult ")
        return signUpResult
    }
    suspend fun getSignIn(signInEmail:String, signInPassword:String): String? {
        val signInResult: String? = repository.getSignIn(signInEmail,signInPassword)
        Log.e("TAG", "getSignUp: ViewModel=========================== $signInResult ")
        return signInResult
    }
    fun readUserData(){
        viewModelScope.launch{
             mutableLiveModel.value = repository.readUserData()
            liveModel=mutableLiveModel
        }
    }
    fun insertUserData(userModel: UserModel){
        viewModelScope.launch {
            repository.insertUserData(userModel)
        }
    }
    fun readAllUsers() {
        viewModelScope.launch {
            usersList = repository.readAllUsers()
        }
    }
    fun readMessage(uids:String): Flow<MutableList<MessageModel>>{
        viewModelScope.launch {
            chatsList = repository.readMessage(uids)
        }
        return chatsList
    }
    fun sendMessage(clientId: String,
                    messageText: String,
                    currentTime: String,
                    getEmail:String,
                    getName:String,
                    getMobile:String) {
        viewModelScope.launch {
            val uid1 = mutableMapOf<String, Any>()
            uid1["email"] = authenticationHelper.user!!.email!!
            uid1["name"] = repository.readUserData().firstName +" "+ repository.readUserData().lastName
            uid1["mobile"] = repository.readUserData().mobile
            uid1["uid"] = repository.readUserData().uid
            uid1["status"] = false
            val uid2 = mutableMapOf<String, Any>()
            uid2["email"] = getEmail
            uid2["name"] = getName
            uid2["mobile"] = getMobile
            uid2["uid"] = clientId
            uid2["status"] = false
            val chatDocModel = ChatDocModel(
                uid1 = uid1,
                uid2 = uid2,
                uids = mutableListOf(authenticationHelper.user!!.uid, clientId)
            )
            val messageModel = MessageModel(
                senderUid = authenticationHelper.user!!.uid,
                msg = messageText,
                dateTime = currentTime,
                docId =chatDocModel.uids.toString()
            )
            Log.d("TAG", "UserViewModel-=-=-=-=-===--====-==--==-==-=$messageModel")
             newChat = repository.sendMessage(clientId, messageModel, chatDocModel)
        }
    }
    fun checkChatUsers(): Flow<MutableList<ChatDocModel>> {
        viewModelScope.launch {
            checkUserLists = repository.checkChatUsers()
        }
        return checkUserLists
    }
    fun deleteMessage(docId:String) {
        return repository.deleteMessage(docId)
    }
}