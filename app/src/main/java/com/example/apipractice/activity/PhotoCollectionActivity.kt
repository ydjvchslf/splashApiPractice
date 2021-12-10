package com.example.apipractice.activity

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.text.InputFilter
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.Toast
//import android.widget.SearchView
import androidx.appcompat.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.apipractice.R
import com.example.apipractice.Utill.Constant.TAG
import com.example.apipractice.databinding.ActivityPhotoBinding
import com.example.apipractice.model.Photo
import com.example.apipractice.recyclerView.PhotoRecyclerApater

class PhotoCollectionActivity : AppCompatActivity(),
                                SearchView.OnQueryTextListener, //material내장 써치뷰
                                CompoundButton.OnCheckedChangeListener,  //material내장 switch
                                View.OnClickListener // 전체삭제btn 이벤트 쓸라고

                                                                    {
    private val photoList = ArrayList<Photo>()

    //뷰바인딩
    private var mBinding : ActivityPhotoBinding? = null
    private val binding get() = mBinding!!

    //서치뷰를 앱바안에서 쓰겠다고 했는데, 실제 xml엔 안보이지만 거기서 갖고있는걸 쓰려고 이렇게 변수 선언한건가?
    private lateinit var mySearchView: SearchView
    private lateinit var mySearchViewEditText: EditText

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
            layoutManager = GridLayoutManager(this@PhotoCollectionActivity, 2) //TODO 이것도 xml에서 설정해줄 수 있지 않을까? span도
            adapter = PhotoRecyclerApater(photoList)
        }

        //써치뷰 부분
        //검색한 keyword를 앱바에 띄워줘야 하니까
        binding.topAppBar.title = keyword
        //어떤 액션바를 사용할지 설정한다
        setSupportActionBar(binding.topAppBar)

        //검색 히스토리 부분 연결
        binding.historySwitch.setOnCheckedChangeListener(this)

        //전체삭제 부분 연결
        binding.historyDeleteBtn.setOnClickListener(this)


    }

    //앱바 서치뷰를 xml에서 설정해주고 앱바 서치뷰가 있는 화면에서 이걸 추가해줘야해
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.top_app_bar_menu, menu)
        Log.d(TAG, "PhotoCollectionActivity - onCreateOptionsMenu() 앱바 서치뷰 실행 ")

        //searchView를 가져오려면 매니저가 필요함
        val searchViewManger = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        this.mySearchView = menu?.findItem(R.id.search_menu)?.actionView as SearchView

        //: SearchView.OnQueryTextListener을 this.activity와 연결
        this.mySearchView.setOnQueryTextListener(this)

        this.mySearchView.apply {
           //this.queryHint xml에서 설정해줬는데 왜 안뜰깡,,, 안해도 됨
            queryHint = "검색어를 입력하세요:)"

            setOnQueryTextFocusChangeListener { _, hasExpand -> //view, boolean
                when(hasExpand){ //펼쳐졌을때
                    true -> {
                        Log.d(TAG, "PhotoCollectionActivity - 서치뷰 열림")
                        //검색어 히스토리 뷰 나오게
                        binding.searchHistoryLinear.visibility = View.VISIBLE
                    }
                    false -> {
                        Log.d(TAG, "PhotoCollectionActivity - 서치뷰 닫힘")
                        binding.searchHistoryLinear.visibility = View.INVISIBLE
                    }
                }
            }
            //서치뷰에서 에딧텍스트를 가져온다
            mySearchViewEditText = this.findViewById(androidx.appcompat.R.id.search_src_text)
        }

        mySearchViewEditText.apply {
            //필터: 글자제한, 칼라적용
            filters = arrayOf(InputFilter.LengthFilter(12))
            setTextColor(android.graphics.Color.WHITE)
            setHintTextColor(android.graphics.Color.WHITE)
        }
        return true
    }


    // : SearchView.OnQueryTextListener 으로써 구현해야 할 메서드
    // 서치뷰 검색어 쓰고 키보드에서 검색버튼 클릭
    override fun onQueryTextSubmit(query: String?): Boolean {
        Log.d(TAG, "PhotoCollectionActivity -onQueryTextSubmit() / 사용자가 입력한 searchTerm임 / query: $query")
        //서치뷰를 닫아버리고, query를 앱바 title로 남겨기
        //app_bar_menu가 포함되어있는 appBar를 무너뜨리는건가
        this.binding.topAppBar.collapseActionView()

        //TODO:: 사진검색 api 호출 (제일 마지막에)
        //TODO:: 검색어 저장

        return true
    }

    // 서치뷰 검색어안에 text가 변화했을때 이벤트
    override fun onQueryTextChange(newText: String?): Boolean {
        Log.d(TAG, "PhotoCollectionActivity -onQueryTextChange() / newText: $newText")
        // 12자 제한,
       // val searchViewUserInputText = newText ?:""

//        val searchViewUserInputText = newText.let {
//            it
//        } ?:""

        if (newText?.count() == 12){
            Toast.makeText(this, "12자 이내로 입력하세요", Toast.LENGTH_SHORT).show()
        }

        return true
    }

    //material switch 인터페이스, implement (CompoundButton이 switch????)
    override fun onCheckedChanged(switch: CompoundButton?, isChecked: Boolean) {
        when(isChecked) {
            true -> {
                Log.d(TAG, "검색기록보기 On")
                binding.historyDeleteBtn.visibility = View.VISIBLE
            }else -> {
                 Log.d(TAG, "검색기록보기 False")
            //히스토리 리싸이클러뷰(이거 쫌 고민), 전체삭제 btn invisible
            binding.historyDeleteBtn.visibility = View.INVISIBLE
            }
        }
    }

    override fun onClick(view: View?) {
        Log.d(TAG, "전체삭제 Btn 클릭됐슴다")
    }


}