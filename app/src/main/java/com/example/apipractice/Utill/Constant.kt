package com.example.apipractice.Utill

object Constant {
    const val TAG = "로그"
    const val BASE_URL = "https://api.unsplash.com"
    const val CLIENT_ID = "I8UBuRXH62hCqOiAjpIygpDV0NTNhOfKmvMchS1sYwM"
}

enum class SEARCH_TYPE {
    PHOTO,
    USER
}

enum class RESPONSE_STATE {
    OKAY,
    FAIL
}