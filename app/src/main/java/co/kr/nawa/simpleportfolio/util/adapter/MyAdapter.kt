package co.kr.nawa.simpleportfolio.util.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import co.kr.nawa.simpleportfolio.R
import co.kr.nawa.simpleportfolio.util.common.logD
import co.kr.nawa.simpleportfolio.holder.ItemVIewHolder
import co.kr.nawa.simpleportfolio.holder.MenuVIewHolder
import co.kr.nawa.simpleportfolio.holder.SubitemVIewHolder


class MyAdapter(var layout: Int, items: ArrayList<out Any>) : RecyclerView.Adapter<ViewHolder>() {

    private var items: ArrayList<out Any>
    var onClickListener: ((View, Int) -> Unit)? = null


    init {
        this.items=items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(layout, parent, false)

        return when(layout){
            R.layout.list_item->  ItemVIewHolder(view, onClickListener)
            R.layout.list_sub_item->  SubitemVIewHolder(view, onClickListener)
            R.layout.list_menu->  MenuVIewHolder(view, onClickListener)
            else -> ItemVIewHolder(view, onClickListener)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
//
    override fun onBindViewHolder(@NonNull holder: ViewHolder, position: Int) {
        holder.init(items[position])

    }


    override fun onViewDetachedFromWindow(holder: ViewHolder) {
//        super.onViewDetachedFromWindow(holder)
        holder.itemView.clearAnimation()
        logD("onViewDetachedFromWindow")
    }

    override fun onViewAttachedToWindow(holder: ViewHolder) {
//        super.onViewAttachedToWindow(holder)
        setAnimation(holder.itemView,0)
        logD("onViewAttachedToWindow")
    }

    var lastPosition=0

    private fun setAnimation(viewToAnimate: View, position: Int) {
        // If the bound view wasn't previously displayed on screen, it's animated
//        if (position > lastPosition) {
            val animation: Animation =
                AnimationUtils.loadAnimation(viewToAnimate.context, R.anim.item_animation_fall_down)
            viewToAnimate.startAnimation(animation)
//            lastPosition = position
//        }
    }



//    fun setOnClickListener (onClickListener: (view:View, position:Int) -> Unit){
//        this.onClickListener = onClickListener
//        notifyDataSetChanged()
//    }

    fun setItems(items: ArrayList<out Any>) {
        this.items = items
        notifyDataSetChanged()
    }


}