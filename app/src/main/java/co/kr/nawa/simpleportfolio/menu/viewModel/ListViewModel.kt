package co.kr.nawa.simpleportfolio.menu.viewModel

import androidx.lifecycle.MutableLiveData
import co.kr.nawa.simpleportfolio.item.Item
import co.kr.nawa.simpleportfolio.util.common.logD
import co.kr.nawa.simpleportfolio.util.common.logE
import co.kr.nawa.simpleportfolio.util.async.Repository
import co.kr.nawa.simpleportfolio.util.basic.ViewModelBasic
import io.reactivex.android.schedulers.AndroidSchedulers

import io.reactivex.schedulers.Schedulers

class ListViewModel(private val repository: Repository) : ViewModelBasic() {


    var items = MutableLiveData<ArrayList<Item>>(ArrayList())

    fun itemsGet(page:Int){
        var data=HashMap<String,String>()
        data["page"] = page.toString()
        data["per_page"] = "30"
        addDisposable(
            repository.getTojson("beers",data, Item::class.java)
                .doOnSubscribe {}
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    logD("ddddd==${it.size}")
                    items.postValue(it)
                },{
                    logE("error=${it.localizedMessage}")
                },{})
        )
    }

}
