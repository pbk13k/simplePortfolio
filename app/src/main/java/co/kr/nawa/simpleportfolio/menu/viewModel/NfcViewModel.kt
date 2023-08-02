package co.kr.nawa.simpleportfolio.menu.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.kr.nawa.simpleportfolio.util.basic.ViewModelBasic


class NfcViewModel: ViewModelBasic() {
    var _str= MutableLiveData<String>()
    val str: LiveData<String> get() = _str
}