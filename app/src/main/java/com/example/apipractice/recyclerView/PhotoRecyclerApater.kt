package com.example.apipractice.recyclerView

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.apipractice.App
import com.example.apipractice.R
import com.example.apipractice.databinding.PhotoItemBinding
import com.example.apipractice.model.Photo

//TODO 생성자에 data 바로 받지 않게 리팩토링, 2단계 : 원래는 뷰모델리스트를 받아야 함
class PhotoRecyclerApater(val items: ArrayList<Photo>)
    : RecyclerView.Adapter<PhotoRecyclerApater.PhotoViewHolder>() {

    //근데 얘는 뷰바인딩을 다 가져올수가 있나..?
    inner class PhotoViewHolder(binding: PhotoItemBinding) : RecyclerView.ViewHolder(binding.root){
    //뷰 접근
        private val photoImageView = binding.photoImgView
        private val photoCreatedAt = binding.date
        private val photoLikesCount = binding.like

        //photo_item에 데이터 끼워주기
        fun bindWithView(photoItem: Photo){
            photoCreatedAt.text = photoItem.createdAt
            photoLikesCount.text = photoItem.likesCount.toString()

            //Glide 이용해서 이미지 로딩
            Glide.with(App.instance)
                .load(photoItem.thumbnail)
                .placeholder(R.drawable.ic_android_black_24dp) // ex) placeholder(R.drawable.loading)
                .into(photoImageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        Log.d("LOG", "PhotoRecyclerApater - onCreateViewHolder 호출")
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.photo_item, parent, false) //item_xml 모양을 하는 뷰 홀더를 생성

        return PhotoViewHolder(PhotoItemBinding.bind(view))
    }

    //홀더와 데이터를 묶어준다
    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bindWithView(items[position])
    }

    override fun getItemCount(): Int = items.size


}