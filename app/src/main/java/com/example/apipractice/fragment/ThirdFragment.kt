package com.example.apipractice.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.apipractice.R
import com.example.apipractice.databinding.FragmentThirdBinding
import com.example.apipractice.utill.Constant

class ThirdFragment : Fragment() {

    private var mBinding : FragmentThirdBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(Constant.TAG, "ThirdFragment - onCreate() called")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(Constant.TAG, "ThirdFragment - onCreateView() called")

        val binding = FragmentThirdBinding.inflate(inflater, container, false)
        mBinding = binding
        return mBinding?.root
    }

    override fun onDestroy() {
        mBinding = null
        super.onDestroy()
    }
}