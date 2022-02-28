package com.iamquan.nowchat.model

data class DataMess(
    var id: String,
    var userNameReceiver : String?= "",
    var lastUser : String? ="",
    var lastmessage : String? ="",
    var avatar : String?="",
)
