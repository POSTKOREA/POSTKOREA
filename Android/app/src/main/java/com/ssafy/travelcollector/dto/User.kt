package com.ssafy.travelcollector.dto

data class User(
    var userId: Int = 0,
    var userEmail: String = "",
    var userPwd: String = "",
    var userName: String = "",
    var userNickname: String = "",
    var userAge: Int = 0,
    var userGender: Int = 0,
    var userAuth: Int = 0
)
