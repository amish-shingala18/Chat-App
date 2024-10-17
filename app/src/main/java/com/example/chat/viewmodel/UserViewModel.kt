package com.example.chat.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chat.domain.DataRepository.Companion.repository
import kotlinx.coroutines.launch

class UserViewModel: ViewModel() {
    fun getSignUp(signUpEmail:String, signUpPassword:String): String? {
        var result: String?=null
        viewModelScope.launch {
            result =repository.getSignUp(signUpEmail,signUpPassword)
        }
        Log.e("TAG", "getSignUp: ViewModel=========================== $result ")
        return result
    }
}