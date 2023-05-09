package co.kr.nawa.simpleportfolio.viewModel


import androidx.lifecycle.MutableLiveData
import co.kr.nawa.simpleportfolio.BuildConfig.VERSION_CODE
import co.kr.nawa.simpleportfolio.BuildConfig.VERSION_NAME
import co.kr.nawa.simpleportfolio.item.Item
import co.kr.nawa.simpleportfolio.util.async.Repository
import co.kr.nawa.simpleportfolio.util.basic.ViewModelBasic
import co.kr.nawa.simpleportfolio.util.common.logD
import co.kr.nawa.simpleportfolio.util.common.logE
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class IntroViewModel(private val repository: Repository):ViewModelBasic() {

    val versionCode:Int = VERSION_CODE
    var versionName = MutableLiveData<String>()
    var versionCheck = MutableLiveData<Boolean>()


    init {
        versionCheck()
    }

    private fun versionCheck(){
//        versionCode.postValue(V)
        versionName.postValue(VERSION_NAME)


        var data=HashMap<String,String>()
        addDisposable(
            repository.getTojsonOne("getVersion",data, "version")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    logD("ddddd")
                    logD("it=$it")

                    if (versionCode < it.toInt()){
                        versionCheck.postValue(true)
                    }else{
                        versionCheck.postValue(false)
                    }


                },{
                    //logE("error=${it.localizedMessage}")
                    versionCheck.postValue(false)
                })
        )

    }

}