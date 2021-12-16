package com.example.apipractice.recyclerView

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.apipractice.R
import com.example.apipractice.utill.Constant.TAG
import com.example.apipractice.utill.SharedPrefManager
import com.example.apipractice.databinding.SearchHistoryItemBinding
import com.example.apipractice.model.SearchHistory
import com.example.apipractice.viewModel.SearchHistoryViewModel

class SearchHistoryRecyclerAdapter(
    private val clickListener:(SearchHistory) -> Unit ,
    private val longClickListener: (SearchHistory) -> Boolean):
    RecyclerView.Adapter<SearchHistoryRecyclerAdapter.SearchHistoryViewHolder>(){

    //SharedPref에 저장된 storedSearchHistoryList 인지, 아니면 그냥 data class SearchHistory?
    private val items = SharedPrefManager.getStoreSearchHistoryList() //as ArrayList<SearchHistory>

    //item 삭제를 위한 index
    private var index: Int = 0

    //수정 전: 생성자(binding: SearchHistroyItemBinding)
    inner class SearchHistoryViewHolder(private val binding: SearchHistoryItemBinding)
                                        : RecyclerView.ViewHolder(binding.root){
        //뷰 가져오기 (뷰바인딩 = xml)

        //search_history_item에 데이터 끼워주기
        fun bindWithView(searchHistoryItem: SearchHistory){
            binding.searchRvTerm.text = searchHistoryItem.term
            binding.searchRvTime.text = searchHistoryItem.timeStamp

            //온클릭리스너 설정
            //TODO:: 정보를 액티비티나 프래그먼트에 알려주기 위해서 콜백함수 필요성!
            binding.constraintSearchItem.setOnClickListener {
                Log.d(TAG, "SearchHistoryRecyclerAdapter : 전체 몸통 클릭됨")
                clickListener(searchHistoryItem)

                Log.d(TAG, "SearchHistoryRecyclerAdapter : value => $searchHistoryItem")
            }

            binding.deleteOneBtn.setOnClickListener {
                Log.d(TAG, "SearchHistoryRecyclerAdapter : 리사이클러뷰 deleteOneBtn 클릭됨!")
                clickListener(searchHistoryItem)
                Log.d(TAG, "SearchHistoryRecyclerAdapter : value => $searchHistoryItem \n" +
                        "term: ${searchHistoryItem.term}, timestampe: ${searchHistoryItem.timeStamp} \n" +
                        "absoluteAdapterPosition: ${this.absoluteAdapterPosition}")
                //index = this.bindingAdapterPosition //현재 포지션임..각 아이템의 포지션이 아니랑
                index = this.absoluteAdapterPosition //이게 진짜 포지션임

                Log.d(TAG, "index 잘 담겼니? => $index")

                deleteOneItem(index)

                Log.d(TAG, "인덱스 삭제 실행 끝!") //어댑터를 다시 열어줘야 최신 데이터 보임..liveData로 안해서 그런가?
            }

            //long press click
            binding.constraintSearchItem.setOnLongClickListener {
                Log.d(TAG, "SearchHistoryRecyclerAdapter : 리사이클러뷰 Long~~~ 클릭됨!")
                longClickListener(searchHistoryItem)
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

    //리사이클러뷰 안에서 item 하나 삭제
    fun deleteOneItem(index: Int){
        Log.d(TAG, "remove 전 리사이클러뷰 item: $items ")

        //term 찾아내서 Shared에서 1개만 삭제
        val keyword = items?.get(index)?.term

        items?.removeAt(index) // index = 리사이클러뷰 안에서의 순서 (역순이라 밑에서부터 0123~)
        notifyDataSetChanged() //리사이클러뷰 갱신


        Log.d(TAG, "선택된 item의 리사이클러 index는 : $index")

        SharedPrefManager.deleteOneSearchHistory(keyword)

        Log.d(TAG, "찾아낸 keyword [ $keyword ] 들고 삭제하러 간다~")

    }




}