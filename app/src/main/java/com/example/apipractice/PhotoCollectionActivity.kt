package com.example.apipractice

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apipractice.Utill.Constant.TAG
import com.example.apipractice.databinding.ActivityPhotoBinding
import com.example.apipractice.model.Photo
import com.example.apipractice.recyclerView.PhotoRecyclerApater

class PhotoCollectionActivity : AppCompatActivity(){

    private val photoList = ArrayList<Photo>()

    //뷰바인딩
    private var mBinding : ActivityPhotoBinding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_photo)
        mBinding = ActivityPhotoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d(TAG, "PhotoCollectionActivity - onCreate() 호출")

       //MainActivity에서 intent에서 받은 data 추출하기
//        bundle.putSerializable("photo_array_list", photoArrayList)
//        intent.putExtra("array_bundle", bundle)
//        intent.putExtra("keyword", keyword)

        val keyword = intent.getStringExtra("keyword")
        val array_bundle = intent.getBundleExtra("array_bundle") //번들에서 그냥 빼면 Object으로 나오나보다
        val photoList = array_bundle?.getSerializable("photo_array_list") as ArrayList<Photo>

       Log.d(TAG, "PhotoCollectionActivity - Main에서 전달받은 intent 확인 / photoArrayList.size: ${photoList.count()}, keyword: $keyword")

    //만든 어댑터 설정
        binding.photoRecyclerView.apply {
            layoutManager = GridLayoutManager(this@PhotoCollectionActivity, 2)
            adapter = PhotoRecyclerApater(photoList)
        }

    }
}