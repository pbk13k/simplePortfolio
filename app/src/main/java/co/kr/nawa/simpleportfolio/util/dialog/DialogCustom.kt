package co.kr.nawa.simpleportfolio.util.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import co.kr.nawa.simpleportfolio.R
import co.kr.nawa.simpleportfolio.databinding.DialogCustomViewBinding


class DialogCustom (val item: DialogItem, val listener: (() -> Unit)): DialogFragment() {



    lateinit var binding: DialogCustomViewBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        dialog?.setCancelable(item.setCancelable)
        binding= DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.dialog_custom_view, null, false
        )

        binding.item=item

        initListener()


        return binding.root
    }

    private fun initListener(){
        binding.btnSend.setOnClickListener {

            listener()
            dismiss()
        }

        binding.btnCancel.setOnClickListener {
            dismiss()
        }

    }


}