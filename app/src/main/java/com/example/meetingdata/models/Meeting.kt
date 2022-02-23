package com.example.meetingdata.models

data class Meeting(
    val timeStamp: String,
    val users: MutableList<User>
)

data class User(
    val id: String,
    val name: String
)