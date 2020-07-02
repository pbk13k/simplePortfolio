package co.kr.nawa.simpleportfolio.viewHolder

import android.view.View
import co.kr.nawa.simpleportfolio.item.Item
import co.kr.nawa.simpleportfolio.util.common.logD
import co.kr.nawa.simpleportfolio.util.adapter.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.list_item.view.*


class ItemVIewHolder(val view: View,var mListener: ((View,Int)->Unit)?): ViewHolder(view),View.OnClickListener {

    val textView=view.textView
    val imageView=view.imageView


    init {
        logD("init")
    }

    override fun onClick(v: View) {
        mListener?.let {
            it(v,adapterPosition)
        }
    }

    override fun init(item: Any) {
        val i=item as Item
        val requestOptions = RequestOptions
            .skipMemoryCacheOf(false)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
        Glide.with(view.context)
            .load(i.image_url)
            .apply(requestOptions)
            .override(100, 100)
            .into(imageView)
        textView.text=i.name
        logD("bind")

        view.setOnClickListener(this)
//        val drawable : GradientDrawable =view.context.getDrawable(R.drawable.imageclip) as (GradientDrawable)
//
//
//        view.imageView.background=drawable
//        view.imageView.clipToOutline=true
    }


}