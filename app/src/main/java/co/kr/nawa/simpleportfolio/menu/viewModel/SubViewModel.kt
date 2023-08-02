package co.kr.nawa.simpleportfolio.menu.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.kr.nawa.simpleportfolio.item.Item
import co.kr.nawa.simpleportfolio.util.basic.ViewModelBasic

class SubViewModel:ViewModelBasic() {


    var _items = MutableLiveData<ArrayList<Item>>()
    val items: LiveData<ArrayList<Item>> get() = _items
    var position:Int=-1

    fun setItem(items: ArrayList<Item>) {
        _items.postValue(items)
    }

}