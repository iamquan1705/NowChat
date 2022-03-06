package com.iamquan.nowchat.vm

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.iamquan.nowchat.model.Chat
import com.iamquan.nowchat.model.DataMess
import com.iamquan.nowchat.model.User
import com.iamquan.nowchat.utils.Utils

class ChatedViewModel : ViewModel() {
    private val _listchated = MutableLiveData<List<DataMess>>()
    val listchated: LiveData<List<DataMess>>
        get() = _listchated


    private val _listuserchated = MutableLiveData<List<User>>()
    val listuserchated: LiveData<List<User>>
        get() = _listuserchated

    init {

        getListChated()
    }
    fun getchated(listus: List<User>, currenID: String) {
        var listDataMess = arrayListOf<DataMess>()
        val reference =
            FirebaseDatabase.getInstance().getReference(Utils.CHATS)
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot in snapshot.children) {
                    val chat = dataSnapshot.getValue(Chat::class.java)
                    if (chat != null) {
                        if (chat.senderid.toString()
                                .equals(currenID) || (chat.receiverid.toString()
                                .equals(currenID))
                        ) {
                        for (us in listus) {
                            var dataMess: DataMess? = null
                                if (chat.senderid.toString().equals(us.id.toString())) {
                                    dataMess = DataMess(
                                        us.id.toString(),
                                        us.username.toString(),
                                        us.id.toString(),
                                        chat.message,
                                        us.avatar.toString()
                                    )

                                } else if (chat.receiverid.toString().equals(us.id.toString())) {
                                    dataMess = DataMess(
                                        us.id.toString(),
                                        us.username.toString(),
                                        currenID,
                                        chat.message,
                                        us.avatar.toString()
                                    )
                                }
                                if (dataMess != null) {
                                    listDataMess.add(dataMess)
                                }
                            }
                        }
                    }
                    _listchated.postValue(listDataMess)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }


    fun getListChated() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            val reference = FirebaseDatabase.getInstance().getReference(Utils.CHATS)
            reference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    var listid = arrayListOf<String>()
                    for (dataSnapshot in snapshot.children) {
                        val chat = dataSnapshot.getValue(Chat::class.java)
                        if (chat != null) {
                            if (currentUser.uid.equals(chat.receiverid)) {
                                if (!listid.contains(chat.senderid.toString())) {
                                    listid.add(chat.senderid.toString())
                                }
                            } else if (currentUser.uid.equals(chat.senderid)) {
                                if (!listid.contains(chat.receiverid.toString())) {
                                    listid.add(chat.receiverid.toString())
                                }
                            }
                        }
                    }
                    var listus = arrayListOf<User>()
                    for (id in listid) {
                        val reference =
                            FirebaseDatabase.getInstance().getReference(Utils.USERS).child(id)
                        reference.addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                var us = snapshot.getValue(User::class.java)!!
                                if (us != null) {
                                    listus.add(us)
                                }
                                _listuserchated.postValue(listus)
                                getchated(listus, currentUser.uid.toString())
                            }

                            override fun onCancelled(error: DatabaseError) {
                                TODO("Not yet implemented")
                            }
                        })
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }
    }
}