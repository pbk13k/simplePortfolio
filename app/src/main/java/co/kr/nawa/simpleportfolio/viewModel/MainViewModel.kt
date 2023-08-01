package co.kr.nawa.simpleportfolio.viewModel


import androidx.lifecycle.MutableLiveData
import co.kr.nawa.simpleportfolio.R
import co.kr.nawa.simpleportfolio.item.MenuItem
import co.kr.nawa.simpleportfolio.menu.view.list.ActivityList
import co.kr.nawa.simpleportfolio.menu.view.LoginActivity
import co.kr.nawa.simpleportfolio.menu.view.NfcActivity
import co.kr.nawa.simpleportfolio.util.basic.ViewModelBasic

class MainViewModel : ViewModelBasic(){

    var items = MutableLiveData<ArrayList<MenuItem>>(ArrayList())

    init {
        val temItems=ArrayList<MenuItem>()
        temItems.add(MenuItem(R.drawable.ic_style,"Animation_List",ActivityList::class.java))
        temItems.add(MenuItem(R.drawable.ic_login,"SNS_Login\nScene_Animation", LoginActivity::class.java))
        temItems.add(MenuItem(R.drawable.ic_nfc,"NFC", NfcActivity::class.java))
        //temitems.add(MenuItem(R.drawable.ic_map,"Map", MapActivity::class.java))
        items.postValue(temItems)
    }



}