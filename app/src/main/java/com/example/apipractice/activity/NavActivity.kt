package com.example.apipractice.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuInflater
import android.view.MenuItem
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.apipractice.R
import com.example.apipractice.databinding.ActivityMainBinding
import com.example.apipractice.databinding.ActivityNavBinding
import com.example.apipractice.utill.Constant.TAG
import com.google.android.material.bottomnavigation.BottomNavigationView

class NavActivity : AppCompatActivity() {

    //뷰바인딩
    private lateinit var binding: ActivityNavBinding

//    //네비게이션 메뉴 버튼 변수 선언
//    private lateinit var bottomNavView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //뷰바인딩 가져오기
        binding = ActivityNavBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //ui에서 navigation다 연결해줬으면 코드로도 연결을 해야지;
        //navHostFragment로 설정한 네비게이션 담는 컨테이너를 찾아오기
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragments_frame) as NavHostFragment

        //네비게이션 컨트롤러
        val navController = navHostFragment.navController

        //뷰에 넣은 네비게이션 메뉴를 네비게이션 컨트롤러와 연결한다
        NavigationUI.setupWithNavController(binding.bottomNav, navController)

        //버튼 클릭 리스너
//        binding.bottomNav.setOnItemSelectedListener { menuItem ->
//            when(menuItem.itemId) {
//                R.id.menu_first -> {
//                    Log.d(TAG, "1 메뉴 클릭")
//                }
//                R.id.menu_second -> {
//                    Log.d(TAG, "2째 메뉴 클릭")
//                }
//                R.id.menu_third -> {
//                    Log.d(TAG, "3번째 메뉴 클릭")
//                }
//            }
//            true
//            }

    }

    private val onBottomNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener{
        when(it.itemId){
            R.id.menu_first -> {
                Log.d(TAG, "NavActivity - 1메뉴 클릭")
            }
            R.id.menu_second -> {
                Log.d(TAG, "NavActivity - 2메뉴 클릭")
            }
            R.id.menu_third -> {
                Log.d(TAG, "NavActivity - 3메뉴 클릭")
            }
        }
        true
    }
}