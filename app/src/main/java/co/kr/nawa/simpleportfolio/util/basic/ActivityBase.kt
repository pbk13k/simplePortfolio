package co.kr.nawa.simpleportfolio.util.basic

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import co.kr.nawa.simpleportfolio.BR
import com.google.android.material.snackbar.Snackbar


abstract class ActivityBase<T : ViewDataBinding,VM: ViewModel>: AppCompatActivity() {


    abstract val layoutResourceId: Int
    lateinit var binding: T
    abstract val viewModel : VM

    protected abstract fun init()

    protected abstract fun init_Listener()
    protected abstract fun init_dataBinding()

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutResourceId)
        binding.setVariable(BR.viewModel,viewModel)
        binding.lifecycleOwner=this

        initDefault()

        init()
        init_Listener()
        init_dataBinding()
    }

    private fun initDefault(){

    }


    fun snackbarShow(str:String){
        Snackbar.make(window.decorView.rootView,str, Snackbar.LENGTH_LONG).show()
    }



}