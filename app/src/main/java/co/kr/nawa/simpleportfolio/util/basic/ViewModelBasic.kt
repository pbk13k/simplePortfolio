package co.kr.nawa.simpleportfolio.util.basic

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

//rxjava2용 뷰모델
open class ViewModelBasic :ViewModel() {

    protected val compositeDisposable = CompositeDisposable()

    //구독 등록
    fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }


    //구독취소 subscribe 취소시킴 (메모리 관리)
    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }


}