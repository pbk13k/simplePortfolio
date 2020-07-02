package co.kr.nawa.simpleportfolio.viewModel


import androidx.lifecycle.MutableLiveData
import co.kr.nawa.simpleportfolio.R
import co.kr.nawa.simpleportfolio.item.MenuItem
import co.kr.nawa.simpleportfolio.menu.view.list.ActivityList
import co.kr.nawa.simpleportfolio.menu.view.LoginActivity
import co.kr.nawa.simpleportfolio.menu.view.MapActivity
import co.kr.nawa.simpleportfolio.util.basic.ViewModelBasic

class MainViewModel : ViewModelBasic(){

    var items = MutableLiveData<ArrayList<MenuItem>>(ArrayList())

    init {
        val temitems=ArrayList<MenuItem>()
        temitems.add(MenuItem(R.drawable.ic_style,"Animation_List",ActivityList::class.java))
        temitems.add(MenuItem(R.drawable.ic_login,"Login", LoginActivity::class.java))
        //temitems.add(MenuItem(R.drawable.ic_map,"Map", MapActivity::class.java))
        items.postValue(temitems)
    }



}