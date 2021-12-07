package com.example.apipractice.Utill

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText


//binding.searchTermEditText.addTextChangedListener(object: TextWatcher {
//    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//        TODO("Not yet implemented")
//    }
//
//    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//       TODO("Not yet implemented")
//    }
//
//    override fun afterTextChanged(p0: Editable?) {
//        이것만 필요해!!
//    }
//
//})


//에딧텍스트에 대한 익스텐션
fun EditText.onMyTextChanged(completion: (Editable?) -> Unit){

    this.addTextChangedListener(object: TextWatcher {
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun afterTextChanged(editable: Editable?) {
        completion(editable)
//        위와 같은 것
//        completion.invoke(editable)
    }

})


}

