package com.iamquan.nowchat.model

data class Chat(
    var message: String?="",
    val senderid: String?="",
    val receiverid: String?="",
    val seen :Boolean?=false
)
