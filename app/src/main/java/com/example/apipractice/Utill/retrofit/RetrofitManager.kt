package com.example.apipractice.Utill.retrofit

import android.util.Log
import com.example.apipractice.Utill.Constant
import com.example.apipractice.Utill.Constant.TAG
import com.example.apipractice.Utill.RESPONSE_STATE
import com.example.apipractice.model.Photo
import com.google.android.material.tabs.TabLayout
import com.google.gson.JsonArray
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
    fun searchPhotos(keyword: String, completion : (RESPONSE_STATE, ArrayList<Photo>?) -> Unit){
        val call: Call<JsonElement>? = apiService?.searchPhotos(keyword)

        call?.enqueue(object : retrofit2.Callback<JsonElement>{
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {

                when (response.code()){
                    200 -> {
                        Log.d(Constant.TAG, "RetrofitManager - 통신 200 성공! \n" +
                                "response: ${response.body()}")

                        //jsonData처리 부분 // 파싱
                        response.body()?.let{ responseBody ->
                            val body = responseBody.asJsonObject
                            val jsonArray = body.getAsJsonArray("results")
                            val sizeOfJsonArray = jsonArray.size()

                            Log.d(TAG, "받아온 jsonArray 갯수: $sizeOfJsonArray")

                            //TODO forEach로 바꿔볼 것
                            val photoDataArray = arrayListOf<Photo>()

                            for(i in 0..jsonArray.size()-1){
                                val resultObject = (jsonArray.get(i)).asJsonObject
                                val user = resultObject.get("user").asJsonObject

                                val author = user.get("username").asString
                                val likesCount = resultObject.get("likes").asInt
                                val createdAt = resultObject.get("created_at").asString
                                val urls = resultObject.get("urls").asJsonObject
                                val thumbnail = urls.get("thumb").asString

                                //Log.d(TAG, "=======잘 파싱 되었나? author: $author, likeCounts: $likesCount, createdAt: $createdAt ,thumbnail: $thumbnail")
                                //파싱한 jsonData를 photo로 만들어주기
                                val photoItem = Photo(
                                    author = author,
                                    likesCount = likesCount,
                                    createdAt = createdAt,
                                    thumbnail = thumbnail
                                )
                                photoDataArray.add(photoItem)
                            }
                            completion(RESPONSE_STATE.OKAY, photoDataArray)
                        }
                    }
                    else -> {
                        Log.d(Constant.TAG, "RetrofitManager - 통신 ${response.code()} 실패!")
                        completion(RESPONSE_STATE.FAIL, null )
                    }
                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d(Constant.TAG, "RetrofitManager - api 아예 통신 실패 / t: $t")
                completion(RESPONSE_STATE.FAIL, null )
            }

        })
    }


}