package com.example.apipractice.recyclerView

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.apipractice.App
import com.example.apipractice.R
import com.example.apipractice.Utill.SharedPrefManager
import com.example.apipractice.databinding.LayoutButtonSearchBinding
import com.example.apipractice.databinding.SearchHistoryItemBinding
import com.example.apipractice.model.Photo
import com.example.apipractice.model.SearchHistory

class SearchHistoryRecyclerAdapter() :
    RecyclerView.Adapter<SearchHistoryRecyclerAdapter.SearchHistoryViewHolder>(){

    //SharedPref에 저장된 storedSearchHistoryList 인지, 아니면 그냥 data class SearchHistory?
    val items = SharedPrefManager.getStoreSearchHistoryList()

    inner class SearchHistoryViewHolder(binding: SearchHistoryItemBinding) : RecyclerView.ViewHolder(binding.root) {

        val searchRvTerm = binding.searchRvTerm
        val searchRvTime = binding.searchRvTime

        //search_history_item에 데이터 끼워주기
        fun bindWithView(searchHistoryItem : SearchHistory){

            searchRvTerm.text = searchHistoryItem.term
            searchRvTime.text = searchHistoryItem.timeStamp

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHistoryViewHolder {
        Log.d("LOG", "SearchHistoryViewHolder - onCreateViewHolder 호출")

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.search_history_item, parent, false)

        return SearchHistoryViewHolder(SearchHistoryItemBinding.bind(view))
    }

    override fun onBindViewHolder(holder: SearchHistoryViewHolder, position: Int) {
        items?.get(position)?.let { holder.bindWithView(it) }

    }

    override fun getItemCount() : Int = items?.size!!


}