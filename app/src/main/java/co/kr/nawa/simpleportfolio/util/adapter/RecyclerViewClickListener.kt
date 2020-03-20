package kr.co.nawa.kotlin.util.adapter

import android.view.View

interface RecyclerViewClickListener {
    fun onClick(view : View, position:Int)
}