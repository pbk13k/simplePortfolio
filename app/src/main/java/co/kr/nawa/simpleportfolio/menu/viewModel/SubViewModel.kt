package co.kr.nawa.simpleportfolio.viewModel

import androidx.lifecycle.MutableLiveData
import co.kr.nawa.simpleportfolio.item.Item
import co.kr.nawa.simpleportfolio.util.`fun`.logD
import co.kr.nawa.simpleportfolio.util.basic.ViewModelBasic

class SubViewModel:ViewModelBasic() {

    var items = MutableLiveData<ArrayList<Item>>()
    var position:Int=-1

    init {
        logD("SubViewModel")
    }

}