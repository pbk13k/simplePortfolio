package co.kr.nawa.simpleportfolio.holder

import android.view.View
import co.kr.nawa.simpleportfolio.util.adapter.ViewHolder

class AnimaitionViewholder (val view: View, var mListener: ((View, Int)->Unit)?): ViewHolder(view),
    View.OnClickListener {

    override fun init(item: Any) {

    }

    override fun onClick(v: View?) {

    }


}