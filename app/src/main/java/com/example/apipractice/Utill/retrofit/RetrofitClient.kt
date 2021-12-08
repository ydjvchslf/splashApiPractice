package com.example.apipractice.Utill.retrofit

import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.example.apipractice.App
import com.example.apipractice.Utill.Constant
import com.example.apipractice.Utill.Constant.TAG
import com.example.apipractice.Utill.isJsonArray
import com.example.apipractice.Utill.isJsonObject
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.util.logging.Handler

object RetrofitClient {
    private var retrofitClient : Retrofit? = null

    fun getClient(baseUrl: String) : Retrofit?{
        // 로그 찍기위해 로깅인터셉터 필요
        // 클라이언트 메모리에 올림
        val client = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger{
            override fun log(message: String) {
                Log.d(TAG, "RetrofitClient - log() 호출 / message : $message")
                //extension 사용
                when {
                    message.isJsonObject() ->
                        Log.d(TAG, "RetrofitClient - log() JsonObject : true \n" +
                                JSONObject(message).toString(4)) //들여쓰기
                    message.isJsonArray() ->
                        Log.d(TAG, "RetrofitClient - log() JsonArray : true \n" +
                                JSONArray(message).toString(4)) //들여쓰기
                    else ->
                        try {
                            Log.d(TAG, "RetrofitClient - Json이 아니야 / messsage: $message")
                        }catch (e: Exception){
                            Log.d(TAG, "RetrofitClient - Json이 아니야 / message: $message")
                        }
                }
            }
        })

        //로깅 인터셉터 레벨 설정
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        //이렇게 설정세팅한 로깅 인터셉터를 연결
        client.addInterceptor(loggingInterceptor)


        //기본 파라미터추가 (client_id 같은거)
        val baseParameterInterceptor : Interceptor = (object: Interceptor{
            override fun intercept(chain: Interceptor.Chain): Response {
                //오리지널 리퀘스트 (BASE_URL 이전!)
                val originalReq = chain.request()

                //쿼리 파라미터 추가 // ?client_id=sdlkfjskdfjd
                val addUrl = originalReq.url.newBuilder().addQueryParameter("client_id", Constant.CLIENT_ID).build()
                val finalUrl = originalReq.newBuilder()
                    .url(addUrl)
                    .method(originalReq.method, originalReq.body)
                    .build()
                val response = chain.proceed(finalUrl) //response

                when (response.code) {
                    200 ->
                        android.os.Handler(Looper.getMainLooper()).post(){
                            Toast.makeText(App.instance, "${response.code} 성공입니다", Toast.LENGTH_SHORT).show()
                        }
                    else ->
                        android.os.Handler(Looper.getMainLooper()).post(){
                            Toast.makeText(App.instance, "${response.code} 실패입니다", Toast.LENGTH_SHORT).show()
                        }

                }

                return response
            }
        })

        //이렇게 만든 기본 파라미터를 연결
        client.addInterceptor(baseParameterInterceptor)



        //기본적인 레트로핏 생성, 빌드
        if (retrofitClient == null){
            retrofitClient = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                    //위에서 만든 로깅인터셉터를 설정
                .client(client.build())
                .build()
        }
        return retrofitClient

    }
}