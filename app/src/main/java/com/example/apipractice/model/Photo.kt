package com.example.apipractice.model

import java.io.Serializable

//json data 파싱해서 가져올 data class
// thumb, likes, created_at 날짜, author

data class Photo(
    var thumbnail: String,
    var likesCount: Int,
    var author: String,
    var createdAt: String
): Serializable{
}