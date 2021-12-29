package com.iamquan.nowchat.model

data class User(
    var username : String?="",
    var password : String?="",
    var avatar : String?="",
    var id : String?="",
    var sex : String?="",
    var phone: String?="",
    var birthday : String?="",
    var email : String?="",
    var status : Int?=1,
    var search : String?=""
)
