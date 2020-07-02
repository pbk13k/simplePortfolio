package co.kr.nawa.simpleportfolio.viewHolder


import android.view.View
import co.kr.nawa.simpleportfolio.item.MenuItem
import co.kr.nawa.simpleportfolio.util.adapter.RecyclerViewClickListener
import co.kr.nawa.simpleportfolio.util.adapter.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.list_menu.view.*


class MenuVIewHolder(val view: View, var mListener: ((View,Int)->Unit)?): ViewHolder(view),View.OnClickListener {

    val menu_text=view.menu_text
    val menu_img=view.menu_img

    override fun onClick(v: View) {
        mListener?.let {
            it(v,adapterPosition)
        }
    }

    override fun init(item: Any) {
        val i=item as MenuItem
        val requestOptions = RequestOptions
            .skipMemoryCacheOf(false)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
        Glide.with(view.context)
            .load(i.img)
            .apply(requestOptions)
            .override(100, 100)
            .into(menu_img)
        menu_text.text=i.text


        view.setOnClickListener(this)
//        val drawable : GradientDrawable =view.context.getDrawable(R.drawable.imageclip) as (GradientDrawable)
//
//
//        view.imageView.background=drawable
//        view.imageView.clipToOutline=true
    }


}