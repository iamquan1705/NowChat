package com.iamquan.nowchat.model

data class DataMess(
    var userSent : String?="",
    var icon : Int?=1,
    var userReceiver : String?= "",
    var message : String? ="",
    var sented : String?=""
)
