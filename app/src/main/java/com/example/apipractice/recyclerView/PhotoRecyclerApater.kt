package com.example.apipractice.recyclerView

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.apipractice.model.Photo

class PhotoRecyclerApater(val items: ArrayList<Photo>)
    : RecyclerView.Adapter<PhotoRecyclerApater.PhotoViewHolder>() {

    inner class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        //뷰들을 가져온다

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        TODO("Not yet implemented")
    }

    //홀더와 데이터를 묶어준다
    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {


    }

    override fun getItemCount(): Int = items.size


}