package com.example.apipractice.recyclerView

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.liveData
import androidx.recyclerview.widget.RecyclerView
import com.example.apipractice.R
import com.example.apipractice.Utill.Constant.TAG
import com.example.apipractice.Utill.SharedPrefManager
import com.example.apipractice.databinding.SearchHistoryItemBinding
import com.example.apipractice.model.SearchHistory

class SearchHistoryRecyclerAdapter(
    private val dataList:List<SearchHistory>,
    private val clickListner:(SearchHistory)->Unit) :
    RecyclerView.Adapter<SearchHistoryRecyclerAdapter.SearchHistoryViewHolder>(){

    //SharedPref에 저장된 storedSearchHistoryList 인지, 아니면 그냥 data class SearchHistory?
    val items = SharedPrefManager.getStoreSearchHistoryList() //as ArrayList<SearchHistory>

    //수정 전: 생성자(binding: SearchHistroyItemBinding)
    inner class SearchHistoryViewHolder(private val binding: SearchHistoryItemBinding) : RecyclerView.ViewHolder(binding.root)

    {
        //뷰 가져오기 (뷰바인딩 = xml)

        //생성자를 전역변수화 시킴으로써 아래와 같은 불필요한 작업 사라짐
        //val searchRvTerm = binding.searchRvTerm
        //val searchRvTime = binding.searchRvTime
        //val deleteOneBtn = binding.deleteOneBtn // 한개한개 삭제해주려고
        //val constraintSearchItem = binding.constraintSearchItem // rv 전체 몸통 클릭할때 처리해주려고

        //search_history_item에 데이터 끼워주기
        fun bindWithView(searchHistoryItem: SearchHistory){
            binding.searchRvTerm.text = searchHistoryItem.term
            binding.searchRvTime.text = searchHistoryItem.timeStamp

            //온클릭리스너 설정
            //TODO:: 정보를 액티비티나 프래그먼트에 알려주기 위해서 콜백함수 필요성!
            binding.constraintSearchItem.setOnClickListener {
                Log.d(TAG, "SearchHistoryRecyclerAdapter : 전체 몸통 클릭됨")
                clickListner(searchHistoryItem)
                Log.d(TAG, "SearchHistoryRecyclerAdapter : value => $searchHistoryItem")
            }

            binding.deleteOneBtn.setOnClickListener {
                Log.d(TAG, "SearchHistoryRecyclerAdapter : 리사이클러뷰 deleteOneBtn 클릭됨!")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHistoryViewHolder {
        Log.d("LOG", "SearchHistoryViewHolder - onCreateViewHolder 호출")

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.search_history_item, parent, false)

        return SearchHistoryViewHolder(SearchHistoryItemBinding.bind(view))
    }

    override fun onBindViewHolder(holder: SearchHistoryViewHolder, position: Int) {

        items?.get(position)?.let {
            holder.bindWithView(items[position]) }

    }

    override fun getItemCount() : Int = items?.size!!


}