package com.example.apipractice.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.apipractice.R
import com.example.apipractice.utill.Constant.TAG
import com.example.apipractice.utill.RESPONSE_STATE
import com.example.apipractice.utill.SEARCH_TYPE
import com.example.apipractice.utill.SharedPrefManager
import com.example.apipractice.utill.onMyTextChanged
import com.example.apipractice.retrofit.RetrofitManager
import com.example.apipractice.databinding.ActivityMainBinding
import com.example.apipractice.model.SearchHistory
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    private var currentSearchType: SEARCH_TYPE = SEARCH_TYPE.PHOTO

    //검색히스토리 변수 선언
    private val searchHistoyList = arrayListOf<SearchHistory>()
    //로드할 기존검색히스토리
    private lateinit var existedSearchHistory: MutableList<SearchHistory>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //view binding 묶어주기
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d(TAG, "MainActivity - onCreate() 호출")

        //SharedPref 저장된 리스트 확인 -> 잘나온닷
        Log.d(TAG, "MainActivity - SharedPref 저장된 리스트 확인 => ${SharedPrefManager.getStoreSearchHistoryList()}")

        //라디오 그룹 가져오기 //radioGroup안쓸꺼니까 _
        binding.searchTermRadioGroup.setOnCheckedChangeListener { _, checkedId ->

            when(checkedId){ //사진검색 버튼이면
                R.id.photo_search_radio_btn -> {
                    Log.d(TAG, "MainActivity - 사진검색 클릭")
                    binding.searchTermLayout.hint = "사진검색" //resources.newTheme()이 뭐야?
                    binding.searchTermLayout.startIconDrawable = resources.getDrawable(R.drawable.picture, resources.newTheme())
                    this.currentSearchType = SEARCH_TYPE.PHOTO
                }

                R.id.user_search_radio_btn -> {
                    Log.d(TAG, "MainActivity - 사용자검색 클릭")
                    binding.searchTermLayout.hint = "사용자검색"
                    binding.searchTermLayout.startIconDrawable = resources.getDrawable(R.drawable.user)
                    this.currentSearchType = SEARCH_TYPE.USER
                }
            }
            Log.d(TAG, "MainActivity - 라디오버튼 클릭 후 currentSearchType : $currentSearchType")
        }

        //텍스트가 변경되었을때 : helperText 사라지기, 검색버튼 나타나기, 스크롤뷰 강제로 위로 올리기, 12자 넘을시 에러 토스트 띄워주기
        //텍스트 변경 메서드 : addTextChangedListener(object: TextWathcer {} 인데 필요한 오버라이드 메서드만 쓰기위해서 따로 뺴는 작업 필요
        //Extension 클래스 생성
        binding.searchTermEditText.onMyTextChanged(completion = {
            if (it.toString().count() > 0){
                Log.d(TAG, "MainActivity - 뭔가를 썼음")
                //include해준건 뷰바인딩이 안되나? -> id를 activity안으로 가져옴
                //또 다른 뷰바인딩을 만들어준 것이라 이렇게 해주면 안됨
//                layoutBinding = LayoutButtonSearchBinding.inflate(layoutInflater)
                //root안쓸거면  binding.frameSearchBtn.frameSearchBtn.visibility 로 접근
                binding.frameSearchBtn.root.visibility = View.VISIBLE

                binding.searchTermLayout.helperText = ""
            }

            if (it.toString().count() == 12){
                Log.d(TAG, "MainActivity - 12자 이상 에러 토스트")
                Toast.makeText(this, "12자 까지만 입력 가능합니다", Toast.LENGTH_SHORT).show()
            }
        })

        //버튼 클릭시, 프로그레스바 보여주기 //TODO :: PhotoCollectionActivity랑 중복
        binding.frameSearchBtn.searchBtn.setOnClickListener {
            Log.d(TAG, "검색버튼 클릭 / currentSearchType : $currentSearchType")
            binding.frameSearchBtn.searchBtn.text = "검색중"
            binding.frameSearchBtn.progressBar.visibility = View.VISIBLE

            val keyword = binding.searchTermEditText.text.toString()

            RetrofitManager.searchPhotos(keyword, completion = {
                responseState, response -> //response : ArrayListof<Photo>
                when(responseState) {
                    RESPONSE_STATE.OKAY -> {
                        Toast.makeText(this, "MainActivity - 호출성공!", Toast.LENGTH_SHORT).show()
                        Log.d(TAG, "response OK! / reponseBody : ${response?.size}") // 10개씩 제공

                        //response헷갈리니까 변수로 정리
                        val photoArrayList = response

                        // TODO:: ViewModel로 빼보기
                        //activity_photo를 호출하고 데이터 전달하기 위해
                        val intent = Intent(this, PhotoCollectionActivity::class.java)
                        //번들로 data로 담을거야 //직렬화로 줄여서 넘기 (이거 찾아보기)
                        val bundle = Bundle()
                        bundle.putSerializable("photo_array_list", photoArrayList)
                        intent.putExtra("array_bundle", bundle)
                        intent.putExtra("keyword", keyword)

                        //검색 히스토리 저장
                        //기존 검색 히스토리 로드
                        existedSearchHistory = SharedPrefManager.getStoreSearchHistoryList()!!

                        val searchHistory = SearchHistory("", "")

                        if (keyword != null) {
                            searchHistory.term = keyword
                        }

                        //검색히스토리 시간 얻어오기, ShardPref 저장하기
                        val currentTime = Date().time
                        val format = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")

                        val searchTermTime = format.format(currentTime) as String

                        //Log.d(TAG, "현재시간은? $searchTermTime")

                        searchHistory.timeStamp = searchTermTime

                        existedSearchHistory.add(searchHistory)

                        //searchHistoyList.add(searchHistory)

                        //SharedPre 저장하기
                        SharedPrefManager.storeSearchHistoryList(existedSearchHistory)

                        Log.d(TAG, "SharedPref 저장된 searchHistoyList : $existedSearchHistory")

                        startActivity(intent)

                    }
                    RESPONSE_STATE.FAIL -> {
                        Toast.makeText(this, "MainActivity 호출실패ㅡㅡ", Toast.LENGTH_SHORT).show()
                        Log.d(TAG, "response No!")
                    }
                }
            })

        }


        //네비게이션 버튼 추가
        binding.navBtn.setOnClickListener {
            Log.d(TAG, "네비버튼 클릭")
            val nextIntent = Intent(this, NavActivity::class.java)
            startActivity(nextIntent)
        }

        //wifi setting
        binding.wifiBtn.setOnClickListener {
            val intent = Intent(Settings.ACTION_WIFI_SETTINGS)
            startActivityForResult(intent, 99)
        }
    }
}