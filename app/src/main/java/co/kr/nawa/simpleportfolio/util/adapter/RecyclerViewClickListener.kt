package co.kr.nawa.simpleportfolio.util.adapter

import android.view.View

interface RecyclerViewClickListener {
    fun onClick(view: View, position: Int)
}