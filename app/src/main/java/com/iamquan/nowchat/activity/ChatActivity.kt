package com.iamquan.nowchat.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.iamquan.nowchat.utils.*
import com.iamquan.nowchat.databinding.ActivityChatBinding
import com.iamquan.nowchat.model.User

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var id = intent.getStringExtra(Utils.ID)
        if (id != null) {
            val reference =
                FirebaseDatabase.getInstance().getReference(Utils.USERS)
                    .child(id)
            reference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.getValue(User::class.java)
                    binding.tvCurrentName.setText(user?.username)
                    Glide.with(applicationContext).load(user?.avatar).into(binding.imgAvtCurrent)
                    if (user?.status == 0) {
                        binding.imgStatusOffChat.setVisibility(View.VISIBLE)
                        binding.imgStatusOnChat.setVisibility(View.GONE)
                    } else if (user?.status == 1) {
                        binding.imgStatusOffChat.setVisibility(View.GONE)
                        binding.imgStatusOnChat.setVisibility(View.VISIBLE)
                    } else {
                        binding.imgStatusOffChat.setVisibility(View.GONE)
                        binding.imgStatusOffChat.setVisibility(View.GONE)
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
    }
}