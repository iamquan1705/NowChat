package com.iamquan.nowchat.model

data class Chat(
    var message: String,
    val sender: String,
    val receiver: String,
    val seen :Boolean
)
