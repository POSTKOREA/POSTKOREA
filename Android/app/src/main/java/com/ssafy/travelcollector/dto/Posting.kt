package com.ssafy.travelcollector.dto

import java.util.Date

data class Posting(
    val postId: Int = 0,
    val userId: Int = 0,
    val postTitle: String,
    val postContent: String,
    val postImg: String,
    val postCreated: Date,
    val postTags: ArrayList<Int> = arrayListOf()
)
