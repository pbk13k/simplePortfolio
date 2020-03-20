package kr.co.nawa.kotlin.util.adapter

import android.content.Context
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.nawa.kotlin.viewholder.Mv_Item
import kr.co.nawa.kotlin.viewholder.ViewModel_DataBinding_Items
import kr.co.nawa.mvvmsimple.R


class MyAdapter() : RecyclerView.Adapter<CustomViewHolder>() {

    lateinit var items:ArrayList<out Any>
    var layout:Int = 0
    lateinit var context:Context
    var handler:Handler?=null
    var recyclerViewClickListener:RecyclerViewClickListener?=null
    lateinit var fragmentManager: FragmentManager




    constructor(layout:Int, context: Context, items:ArrayList<out Any>):this(){
        this.items= items
        this.layout=layout
        this.context=context
    }

    //item
    constructor(layout:Int,context: Context ,items:ArrayList<out Any>,handler:Handler) :
            this(layout,context ,items) {
        this.handler=handler
    }


    constructor(layout: Int, context: Context, items:ArrayList<out Any>,fragmentManager: FragmentManager) : this(layout,context ,items){
        this.fragmentManager=fragmentManager
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): CustomViewHolder {


        var view : View = LayoutInflater.from(context).inflate(layout,parent,false)

        return when(layout){
            R.layout.mvvm_holder-> Mv_Item(view,recyclerViewClickListener)
            R.layout.mvvm_databing_holder-> ViewModel_DataBinding_Items(DataBindingUtil.inflate(
                LayoutInflater.from(context),layout,parent,false
            )!!)
            else -> null!!

        }

    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(viewholder: CustomViewHolder, position: Int) {
        Log.d("adp","onBindViewHolder")
        viewholder.initView(items.get(position),context)
    }


}