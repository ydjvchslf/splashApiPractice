package com.example.apipractice.Utill

import android.content.Context
import android.util.Log
import com.example.apipractice.App
import com.example.apipractice.Utill.Constant.TAG
import com.example.apipractice.model.SearchHistory
import com.google.gson.Gson

object SharedPrefManager {

    private const val SHARED_SEARCH_HISTORY = "shared_search_history"
    private const val KEY_SEARCH_HISTORY = "key_search_history"

    //검색목록 저장 // SearchHistory List로 받아서 저장
    fun storeSearchHistoryList(searchHistory: MutableList<SearchHistory>){
        Log.d(TAG, "SharedPrefManager - storeSearchHistoryList() 검색목록 저장")

        //매개변수로 들어온 배열->문자열 변환(Gson 이용)
        val searchHistoryListString : String = Gson().toJson(searchHistory)
        Log.d(TAG, "SharedPrefManager - Gson 문자열 변환 -> searchHistoryListString: $searchHistoryListString")

        //쉐어드 가져오기
        val shard = App.instance.getSharedPreferences(SHARED_SEARCH_HISTORY, Context.MODE_PRIVATE)
        val editor = shard.edit()

        //저장하기
        editor.putString(KEY_SEARCH_HISTORY, searchHistoryListString)

        //저장한것을 적용
        editor.apply()
    }

    //검색목록 가져오기
    fun getStoreSearchHistoryList() : MutableList<SearchHistory>?{
        val shared = App.instance.getSharedPreferences(SHARED_SEARCH_HISTORY, Context.MODE_PRIVATE)

        val storedSearchHistoryListString = shared.getString(KEY_SEARCH_HISTORY, "")!!

        var storedSearchHistoryList = ArrayList<SearchHistory>()

        if (storedSearchHistoryListString.isNotEmpty()){
            storedSearchHistoryList = Gson()
                                        .fromJson(storedSearchHistoryListString, Array<SearchHistory>::class.java)
                                        .toMutableList() as ArrayList<SearchHistory>
        }
        return storedSearchHistoryList
    }
}