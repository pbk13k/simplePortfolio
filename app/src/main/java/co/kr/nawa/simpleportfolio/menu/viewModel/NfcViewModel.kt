package co.kr.nawa.simpleportfolio.menu.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.kr.nawa.simpleportfolio.util.basic.ViewModelBasic


class NfcViewModel: ViewModelBasic() {


    var _str= MutableLiveData<String>("NFC를 가져다 되면 읽습니다.")
    val str: LiveData<String> get() = _str


    fun setStr(s: String) {
        _str.postValue(s)
    }
}