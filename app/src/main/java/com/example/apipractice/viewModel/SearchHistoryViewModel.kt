package com.example.apipractice.viewModel

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apipractice.App
import com.example.apipractice.model.SearchHistory
import com.example.apipractice.utill.Constant.TAG
import com.example.apipractice.utill.SharedPrefManager
import java.text.SimpleDateFormat
import java.util.*

// ViewModel은 UI 관련 데이터를 저장하고 관리
class SearchHistoryViewModel() : ViewModel() {

    //변경가능한 MutableLiveData 객체 생성
    private var _searchHistory = MutableLiveData<ArrayList<SearchHistory>>()

    //MutableLiveData를 ViewModel의 LiveData에 넣는다
    val searchHistoryList : LiveData<ArrayList<SearchHistory>>
        get() = _searchHistory

    //var shared: SharedPreferences? = App.instance.getSharedPreferences(SharedPrefManager.SHARED_SEARCH_HISTORY, Context.MODE_PRIVATE)

    //검색 히스토리 중복체크
    fun checkDuplicationOfTerm(term: String){
        Log.d(TAG, "searchHistoryViewModel - checkDuplicationOfTerm() 호출")

        val existSearchHistoryList = SharedPrefManager.getStoreSearchHistoryList()

        //for문 돌려 term값 찾아내기
        (0 until existSearchHistoryList?.size!!).forEach { index -> // list안의 index
            Log.d(TAG, "existSearchHistoryList : ${existSearchHistoryList[index]}")

            if (term == existSearchHistoryList[index].term) {
                existSearchHistoryList.removeAt(index) //기존꺼 지웠고
                //새로 추가
                //날짜 얻기
                val currentTime = Date().time
                val format = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")

                val searchTermTime = format.format(currentTime) as String
                val newSearchHistory = SearchHistory(term, searchTermTime)

                existSearchHistoryList.add(newSearchHistory)

                //shared에 저장
                SharedPrefManager.storeSearchHistoryList(existSearchHistoryList)
            }
        }
    }


}