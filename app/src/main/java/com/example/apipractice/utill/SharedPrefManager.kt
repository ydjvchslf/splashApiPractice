package com.example.apipractice.utill

import android.content.Context
import android.util.Log
import com.example.apipractice.App
import com.example.apipractice.utill.Constant.TAG
import com.example.apipractice.model.SearchHistory
import com.google.gson.Gson


object SharedPrefManager {

    private const val SHARED_SEARCH_HISTORY = "shared_search_history"
    private const val KEY_SEARCH_HISTORY = "key_search_history"

    //삭제하기 위한 index
    private var index: Int = 0

    //검색목록 저장 // SearchHistory List로 받아서 저장
    fun storeSearchHistoryList(searchHistory: MutableList<SearchHistory>) {
        Log.d(TAG, "SharedPrefManager - storeSearchHistoryList() 검색목록 저장 호출")

        //매개변수로 들어온 배열->문자열 변환(Gson 이용)
        val searchHistoryListString: String = Gson().toJson(searchHistory)
        Log.d(
            TAG,
            "SharedPrefManager - Gson 문자열 변환 -> searchHistoryListString: $searchHistoryListString"
        )

        //쉐어드 가져오기
        val shard = App.instance.getSharedPreferences(SHARED_SEARCH_HISTORY, Context.MODE_PRIVATE)
        val editor = shard.edit()

        //저장하기
        editor.putString(KEY_SEARCH_HISTORY, searchHistoryListString)

        //저장한것을 적용
        editor.apply()
    }

    //검색목록 가져오기
    fun getStoreSearchHistoryList(): MutableList<SearchHistory>? {
        Log.d(TAG, "SharedPrefManager - getStoreSearchHistoryList() 검색목록 조회 호출")

        val shared = App.instance.getSharedPreferences(SHARED_SEARCH_HISTORY, Context.MODE_PRIVATE)

        val storedSearchHistoryListString = shared.getString(KEY_SEARCH_HISTORY, "")!!

        var storedSearchHistoryList = ArrayList<SearchHistory>()

        if (storedSearchHistoryListString.isNotEmpty()) {
            storedSearchHistoryList = Gson()
                .fromJson(storedSearchHistoryListString, Array<SearchHistory>::class.java)
                .toMutableList() as ArrayList<SearchHistory>
        }
        return storedSearchHistoryList
    }

    fun deleteAllSearchHistory() {

        val shared = App.instance.getSharedPreferences(SHARED_SEARCH_HISTORY, 0)
        val editor = shared.edit()
        editor.clear().commit()
    }

    //검색기록 1개만 삭제하기
    fun deleteOneSearchHistory(keyword: String?) {
        Log.d(TAG, "SharedPrefManager - deleteOneSearchHistory() 검색기록 1개 삭제 호출")
//        먼저 get해서 ArrayList안에 keyword랑 같은것을 삭제
        val storedSearchHistoryList = getStoreSearchHistoryList()

        Log.d(TAG, "삭제전 listSize : ${storedSearchHistoryList?.size}")

        //List for문 돌려서 SearchHisory.term = keword 랑 같으면
        (0 until storedSearchHistoryList?.size!!).forEach { index -> // list안의 index
            Log.d(TAG, "Shared - list에 담긴 각각의 서치 히스토리 : ${storedSearchHistoryList[index]}")

            if (keyword.equals(storedSearchHistoryList[index].term)) {
                this.index = index
                Log.d(TAG, "Shared - keyword가 들은 list의 index => $index")

            }
        }
            //제거한 새로운 SearchHistroyList
            storedSearchHistoryList.removeAt(index)
            Log.d(TAG, "Shared - 제거한 새로운 list: $storedSearchHistoryList")
//            // K,V 형식으로 저장되어 있으니 동일한 K에 item이 하나 삭제되어 업데이트된 araryList를 다시 저장

            storeSearchHistoryList(storedSearchHistoryList)

            Log.d(TAG, "삭제후 listSize : ${storedSearchHistoryList?.size}")

    }
}



