package com.iamquan.nowchat.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.iamquan.nowchat.model.User
import com.iamquan.nowchat.utils.Utils

class ListUserViewModel : ViewModel() {
    private val _listuser = MutableLiveData<List<User>>()
    val listuser: LiveData<List<User>>
        get() = _listuser

    init {
        allUser()
    }
    fun allUser() {
        val lus = arrayListOf<User>()
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            val reference = FirebaseDatabase.getInstance().getReference(Utils.USERS)
            reference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    lus.clear()
                    for (dataSnapshot in snapshot.children) {
                        val user = dataSnapshot.getValue(User::class.java)
                        if (user != null) {
                            if (!user.id.equals(currentUser.uid)) {
                                lus.add(user)
                            }
                        }
                    }
                    _listuser.postValue(lus)
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }
    }
}