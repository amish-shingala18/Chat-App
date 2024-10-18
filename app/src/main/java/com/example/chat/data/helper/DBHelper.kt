package com.example.chat.data.helper

import com.example.chat.data.helper.AuthenticationHelper.Companion.authenticationHelper
import com.example.chat.data.model.UserModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class DBHelper {
    companion object {
        val dbHelper = DBHelper()
    }
    private val userStore = FirebaseFirestore.getInstance()
    fun insertUserData(userModel: UserModel) {
        userStore.collection("users")
            .document(authenticationHelper.user!!.uid)
            .set(userModel)
    }
    suspend fun readUserData(): UserModel {
       val doc =  userStore.collection("users")
            .document(authenticationHelper.user!!.uid)
            .get().await()
       val userGetData = doc.data
       val email = userGetData?.get("email")
       val firstName = userGetData?.get("firstName")
       val lastName = userGetData?.get("lastName")
       val mobile = userGetData?.get("mobile")
       val uid = userGetData?.get("uid")
       return UserModel(email = email.toString(),
           firstName = firstName.toString(),
           lastName = lastName.toString(),
           mobile = mobile.toString(),
           uid = uid.toString()
       )
   }
    fun readAllUsers(): Flow<MutableList<UserModel>> {
    return callbackFlow {
        val snapshot = userStore
            .collection("users")
            .addSnapshotListener { value, error ->
                if (value != null) {
                    val userList = mutableListOf<UserModel>()
                    for (x in value.documents) {
                        val userModel = UserModel(
                            firstName = x["firstName"].toString(),
                            lastName = x["lastName"].toString(),
                            email = x["email"].toString(),
                            mobile = x["mobile"].toString(),
                            uid = x["uid"].toString()
                        )
                        userList.add(userModel)
                    }
                    trySend(userList)
                }
                else{
                    close(error!!)
                }
            }
            awaitClose {
                snapshot.remove()
            }
        }
    }
}