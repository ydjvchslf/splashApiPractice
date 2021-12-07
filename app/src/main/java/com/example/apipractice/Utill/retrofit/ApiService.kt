package com.example.apipractice.Utill.retrofit

import com.google.gson.JsonElement
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    // search/photos/?query=""& id값도 넣긴 넣어줘야해 나중에!!
    @GET ("/search/photos")
    fun searchPhotos(@Query("query") keyword: String) : Call<JsonElement>

    @GET ("/search/users")
    fun searchUsers(keyword: String) : Call<JsonElement>

}