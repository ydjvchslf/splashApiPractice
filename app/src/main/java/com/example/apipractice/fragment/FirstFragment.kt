package com.example.apipractice.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.apipractice.R
import com.example.apipractice.databinding.FragmentFirstBinding
import com.example.apipractice.utill.Constant
import com.example.apipractice.utill.Constant.TAG

class FirstFragment : Fragment() {

    //뷰바인딩 적용
    private var mBinding: FragmentFirstBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(Constant.TAG, "FirstFragment - onCreate() called")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(Constant.TAG, "FirstFragment - onCreateView() called")

        val binding = FragmentFirstBinding.inflate(inflater, container, false)

        mBinding = binding

        return mBinding?.root
    }

    // Fragment를 안고있는 액티비티에 붙었을 때
    override fun onAttach(context: Context) {
        super.onAttach(context)

        Log.d(TAG, "HomeFragment - onAttach()")
    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }
}