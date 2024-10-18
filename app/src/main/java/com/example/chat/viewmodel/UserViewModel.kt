package com.example.chat.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chat.data.model.UserModel
import com.example.chat.domain.DataRepository.Companion.repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class UserViewModel: ViewModel() {
    private var mutableLiveModel = MutableLiveData<UserModel>()
    var liveModel: LiveData<UserModel> = mutableLiveModel
    var usersList: Flow<MutableList<UserModel>> = MutableStateFlow(mutableListOf())
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
}