package co.kr.nawa.simpleportfolio.viewModel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.kr.nawa.simpleportfolio.BuildConfig.VERSION_CODE
import co.kr.nawa.simpleportfolio.BuildConfig.VERSION_NAME
import co.kr.nawa.simpleportfolio.item.Item
import co.kr.nawa.simpleportfolio.menu.viewModel.LoginViewModel
import co.kr.nawa.simpleportfolio.util.async.Repository
import co.kr.nawa.simpleportfolio.util.basic.ViewModelBasic
import co.kr.nawa.simpleportfolio.util.common.logD
import co.kr.nawa.simpleportfolio.util.common.logE
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class IntroViewModel(private val repository: Repository):ViewModelBasic() {

    val versionCode:Int = VERSION_CODE
    var _versionName = MutableLiveData<String>()
    val versionName: LiveData<String> get() = _versionName

    var _versionCheck = MutableLiveData<Boolean>()
    val versionCheck: LiveData<Boolean> get() = _versionCheck

    init {
        versionCheck()
    }

    private fun versionCheck(){
//        versionCode.postValue(V)
        _versionName.postValue(VERSION_NAME)


        var data=HashMap<String,String>()
        addDisposable(
            repository.getTojsonOne("getVersion",data, "version")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    logD("ddddd")
                    logD("it=$it")

                    if (versionCode < it.toInt()){
                        _versionCheck.postValue(true)
                    }else{
                        _versionCheck.postValue(false)
                    }


                },{
                    //logE("error=${it.localizedMessage}")
                    _versionCheck.postValue(false)
                })
        )

    }

}