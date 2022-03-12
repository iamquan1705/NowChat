package com.iamquan.nowchat.utils

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

object Utils {
    const val AVATAR = "avatar"
    const val USER_NAME = "username"
    const val PASSWORD = "password"
    const val ID = "id"
    const val EMAIL = "email"
    const val SEX = "sex"
    const val PHONE = "phone"
    const val BIRTHDAY = "birthday"
    const val STATUS = "status"
    const val SEARCH = "search"
    const val USERS = "users"
    const val DEFAULT = "default"

    const val UPLOADS = "uploads"

    const val MESSAGE= "message"
    const val SENDER_ID= "senderid"
    const val RECEIVER_ID= "receiverid"
    const val SEEN= "seen"
    const val CHATS= "chats"

    const val TOKEN = "token"

    fun updateOnlineStatus(status: Int) {
        val firebaseAuth = FirebaseAuth.getInstance()
        var uid = firebaseAuth.uid
        val databaseReference =
            FirebaseDatabase.getInstance().getReference(USERS).child(uid!!)
        val map = HashMap<String, Any>()
        map[STATUS] = status
        databaseReference.updateChildren(map)
    }

}