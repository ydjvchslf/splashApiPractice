package com.example.apipractice.Utill

object Constant {
    const val TAG: String = "로그"
    const val BASE_URL = "https://api.unsplash.com"
}

enum class SEARCH_TYPE {
    PHOTO,
    USER
}

enum class RESPONSE_STATE {
    OKAY,
    FAIL
}