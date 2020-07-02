package co.kr.nawa.simpleportfolio.util.basic


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import co.kr.nawa.simpleportfolio.BR


abstract class FragmentBase<T : ViewDataBinding,VM: ViewModel> : Fragment() {

    abstract val layoutResourceId: Int

    lateinit var viewDataBinding: T

    lateinit var title:String

    abstract val viewModel : VM

    protected abstract fun init()
    protected abstract fun init_Listener()
    protected abstract fun init_dataBinding()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        viewDataBinding = DataBindingUtil.inflate(inflater, layoutResourceId, container, false)
        viewDataBinding.setVariable(BR.viewModel,viewModel)
        viewDataBinding.lifecycleOwner=this

        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        init_default()

        init()
        init_Listener()
        init_dataBinding()
    }



    fun init_default(){


    }






}