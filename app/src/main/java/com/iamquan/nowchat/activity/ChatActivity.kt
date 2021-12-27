package com.iamquan.nowchat.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.iamquan.nowchat.R
import com.iamquan.nowchat.databinding.ActivityChatBinding

class ChatActivity : AppCompatActivity() {
    private lateinit var binding : ActivityChatBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}