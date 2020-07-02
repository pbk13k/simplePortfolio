package co.kr.nawa.simpleportfolio.menu.viewModel

import androidx.lifecycle.MutableLiveData
import co.kr.nawa.simpleportfolio.util.common.logD
import co.kr.nawa.simpleportfolio.util.async.Repository
import co.kr.nawa.simpleportfolio.util.basic.ViewModelBasic

class LoginViewModel(private val repository: Repository):ViewModelBasic() {

    enum class Type constructor() {
        MAIN,LOGIN,JOIN
    }


    var type = MutableLiveData<Type>()


    init {
        logD("LoginViewModel")
    }



}