package com.example.chat.data.helper

import android.util.Log
import com.example.chat.data.helper.AuthenticationHelper.Companion.authenticationHelper
import com.example.chat.data.model.ChatDocModel
import com.example.chat.data.model.MessageModel
import com.example.chat.data.model.UserModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await


@Suppress("NAME_SHADOWING", "NAME_SHADOWING", "NAME_SHADOWING", "UNCHECKED_CAST")
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
        val doc = userStore
            .collection("users")
            .document(authenticationHelper.user!!.uid)
            .get().await()
        val userGetData = doc.data
        val email = userGetData?.get("email")
        val firstName = userGetData?.get("firstName")
        val lastName = userGetData?.get("lastName")
        val mobile = userGetData?.get("mobile")
        val uid = userGetData?.get("uid")
        return UserModel(
            email = email.toString(),
            firstName = firstName.toString(),
            lastName = lastName.toString(),
            mobile = mobile.toString(),
            uid = uid.toString()
        )
    }

    fun readAllUsers(): Flow<MutableList<UserModel>> {
        return callbackFlow {
            val snapshot = userStore.collection("users")
                .whereNotEqualTo("uid", authenticationHelper.user?.uid)
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
                    } else {
                        close(error!!)
                    }
                }
            awaitClose {
                snapshot.remove()
            }
        }
    }

    private suspend fun checkChatDoc(clientUid: String): QuerySnapshot? {
        val docs = userStore.collection("chats")
            .whereEqualTo("uids", listOf(authenticationHelper.user!!.uid, clientUid))
            .get().await()
        if (docs.documents.size == 0) {
            val docs = userStore.collection("chats")
                .whereEqualTo("uids", listOf(clientUid, authenticationHelper.user!!.uid))
                .get().await()
            return docs
        }
        return docs
    }
    fun checkChatUsers(): Flow<MutableList<ChatDocModel>> {
        Log.e("TAG", "checkChatUsers: ${authenticationHelper.user!!.uid}")
        return callbackFlow {
            val doc = userStore.collection("chats").get().await()
            if (doc!=null) {
                val snapshot = userStore.collection("chats")
                    .whereArrayContains("uids", authenticationHelper.user!!.uid)
                    .addSnapshotListener { value, error ->
                        val userList = mutableListOf<ChatDocModel>()
                        if (value != null) {
                            for (x in value.documents) {
                                val uids = x["uids"] as MutableList<String>
                                val uid1 = x["uid1"] as MutableMap<String, String>
                                val uid2 = x["uid2"] as MutableMap<String, String>

                                val model = ChatDocModel(uid1 = uid1, uid2 = uid2, uids = uids)
                                userList.add(model)
                            }
                            trySend(userList)
                        } else {
                            close(error)
                        }

                    }
                awaitClose {
                    snapshot.remove()
                }
            }
        }

    }
    suspend fun sendMessage(clientId: String, model: MessageModel, docModel: ChatDocModel):Int {
        val docId: String?
        val docs = checkChatDoc(clientId)
        if (docs!!.documents.size == 0) {
            //create new chat
            val docRef = userStore.collection("chats")
                .add(docModel).await()
            docId = docRef.id
            userStore.collection("chats")
                .document(docId)
                .collection("msg")
                .add(model)
            return 0
        } else {
            docId = docs.documents[0].id
            userStore.collection("chats")
                .document(docId)
                .collection("msg")
                .add(model)
            return 1
        }

    }
    fun readMessage(uids: String): Flow<MutableList<MessageModel>> {
        return callbackFlow {
            val docIDs = checkChatDoc(uids)
            Log.e("Data", "readMessage: ${docIDs!!.documents.size}")
                val snapshot = docIDs.documents.lastOrNull()?.id?.let {
                    userStore.collection("chats")
                        .document(it)
                        .collection("msg")
                        .orderBy("dateTime")
                        .addSnapshotListener { value, error ->
                            val messageList = mutableListOf<MessageModel>()
                            if (value != null) {
                                for (document in value.documents) {
                                    val messageModel = MessageModel(
                                        senderUid = document["senderUid"].toString(),
                                        msg = document["msg"].toString(),
                                        dateTime = document["dateTime"].toString()
                                    )
                                    messageList.add(messageModel)
                                }
                                trySend(messageList)
                            } else {
                                Log.e("TAG", "readMessage:------------- ${error!!.message} ")
                                close(error)
                            }
                        }
                }
                awaitClose {
                    snapshot?.remove()
                }

        }
    }
}