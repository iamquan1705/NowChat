package com.iamquan.nowchat.activity

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.gson.Gson
import com.iamquan.nowchat.adapter.ChatAdapter
import com.iamquan.nowchat.databinding.ActivityChatBinding
import com.iamquan.nowchat.model.Chat
import com.iamquan.nowchat.model.User
import com.iamquan.nowchat.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.iamquan.nowchat.notification.*
class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    private var mReceiverId: String = ""
    private var mSender: String = ""
    private lateinit var adapter: ChatAdapter
    private var mChatList = arrayListOf<Chat>()
    private var mAvatar: String = ""
    var topic = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)


        var id = intent.getStringExtra(Utils.ID)
        mReceiverId = id.toString()
        val currentUser = FirebaseAuth.getInstance().currentUser
        mSender = currentUser?.uid.toString()
        if (id != null) {
            val reference =
                FirebaseDatabase.getInstance().getReference(Utils.USERS)
                    .child(id)
            reference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.getValue(User::class.java)
                    binding.tvCurrentName.setText(user?.username)
                    mAvatar = user?.avatar.toString()
                    Glide.with(applicationContext).load(user?.avatar).into(binding.imgAvtCurrent)
                    if (user?.status == 0) {
                        binding.imgStatusOnChat.setVisibility(View.GONE)
                    } else if (user?.status == 1) {
                        binding.imgStatusOnChat.setVisibility(View.VISIBLE)
                    } else {
                        binding.imgStatusOnChat.setVisibility(View.GONE)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }
        binding.btnBack.setOnClickListener {
            finish()
        }
        getMessage(mSender, mReceiverId, this)
    }

    override fun onResume() {
        super.onResume()
        binding.btnSent.setOnClickListener {
            if (binding.edtMessage.text.toString().isEmpty()) {

            } else {
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    var message: String = binding.edtMessage.text.toString().trim { it <= ' ' }
                    var senderId = FirebaseAuth.getInstance().currentUser!!.uid
                    sentMessage(message, senderId, mReceiverId)
                }
                topic = "/topics/$mReceiverId"
                PushNotification(NotificationData("iamquan",binding.edtMessage.text.toString()),
                    topic).also {
                    sendNotification(it)
                }
            }
        }
        Utils.updateOnlineStatus(1)
    }

    override fun onPause() {
        super.onPause()
        Utils.updateOnlineStatus(0)
    }
    fun getMessage(senderId: String, receiverId: String, context: Context) {
        val reference =
            FirebaseDatabase.getInstance().getReference(Utils.CHATS)
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                mChatList.clear()
                for (snap in snapshot.children) {
                    val chat = snap.getValue(Chat::class.java)
                    if (chat != null) {
                        if ((receiverId.equals(chat.receiverid) && senderId.equals(chat.senderid))
                            || receiverId.equals((chat.senderid)) && senderId.equals(chat.receiverid)
                        ) {
                            mChatList.add(chat)
                        }
                        adapter = ChatAdapter(mChatList, context, mAvatar)
                        binding.rvMessage.layoutManager =
                            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                        binding.rvMessage.adapter = adapter
                        adapter.notifyDataSetChanged()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    fun sentMessage(message: String, senderId: String, receiverId: String) {
        if (!TextUtils.isEmpty(message)) {
            val map = HashMap<String, Any>()
            map[Utils.MESSAGE] = message
            map[Utils.SENDER_ID] = senderId
            map[Utils.RECEIVER_ID] = receiverId
            map[Utils.SEEN] = false
            val reference = FirebaseDatabase.getInstance().reference
            reference.child(Utils.CHATS).push().setValue(map)
            binding.edtMessage.setText("")
        }
    }

//    fun getToken() {
//        val databaseReference =
//            FirebaseDatabase.getInstance().getReference(Utils.USERS).child(mReceiverId!!)
//        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                if (snapshot.exists()) {
//                    val token = snapshot.child("token").value.toString()
//                    val name = snapshot.child(Utils.USER_NAME).value.toString()
//
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//
//        })
//    }

    private fun sendNotification(notification: PushNotification) = CoroutineScope(Dispatchers.IO).launch {
        try {
            val response = RetrofitInstance.api.postNotification(notification)
            if(response.isSuccessful) {
                Log.d("TAG", "Response: ${Gson().toJson(response)}")
            } else {
                Log.e("TAG", response.errorBody()!!.string())
            }
        } catch(e: Exception) {
            Log.e("TAG", e.toString())
        }
    }


}