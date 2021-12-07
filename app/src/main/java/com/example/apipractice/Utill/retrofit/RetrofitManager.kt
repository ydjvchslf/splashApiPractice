package com.example.apipractice.Utill.retrofit

import android.util.Log
import com.example.apipractice.Utill.Constant
import com.example.apipractice.Utill.RESPONSE_STATE
import com.google.android.material.tabs.TabLayout
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.create

object RetrofitManager {
    // ApiService 인터페이스 인스턴스 생성
    val apiService: ApiService? = RetrofitClient.getClient(Constant.BASE_URL)?.create(ApiService::class.java)

    //사진검색 api호출 -> query로 받은 keyword를 넣어서 검색해야하니까, parameter로 keyword 넣음
    // unsplach api호출하면 jsondata가 오므로 그 결과값을 받기 위해 completion 변수에 담아 받을거야
    fun searchPhotos(keyword: String, completion : (RESPONSE_STATE, String) -> Unit){
        val call: Call<JsonElement>? = apiService?.searchPhotos(keyword)

        call?.enqueue(object : retrofit2.Callback<JsonElement>{
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                Log.d(Constant.TAG, "RetrofitManager - api 성공! \n" +
                        "response: ${response.body()}")
                completion(RESPONSE_STATE.OKAY, response.body().toString())
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d(Constant.TAG, "RetrofitManager - api 실패ㅡㅡ / t: $t")
                completion(RESPONSE_STATE.FAIL, t.toString())
            }

        })
    }


}