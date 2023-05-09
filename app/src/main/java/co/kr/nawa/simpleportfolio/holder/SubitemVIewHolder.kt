package co.kr.nawa.simpleportfolio.holder

import android.view.View
import android.view.animation.AccelerateInterpolator
import co.kr.nawa.simpleportfolio.databinding.ListSubItemBinding
import co.kr.nawa.simpleportfolio.item.Item
import co.kr.nawa.simpleportfolio.util.adapter.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.extensions.LayoutContainer


class SubitemVIewHolder(private val binding: ListSubItemBinding, var mListener:((View,Int)->Unit)?) :
    ViewHolder(binding.root),View.OnClickListener, LayoutContainer {

    override val containerView: View
        get() = binding.root



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
        Glide.with(binding.root.context)
            .load(i.image_url)
            .apply(requestOptions)
            .override(100, 100)
            .into(binding.subimg)
//        view.textView.text=i.name

        val interpolator= AccelerateInterpolator()
//        view.animate()
//            .scaleX(1f).scaleY(1f).alpha(1f).translationY(-30f )
//            .setDuration(3000).setInterpolator(interpolator).withEndAction {
//                view.animate().translationY(0f).setDuration(300).setInterpolator(interpolator).start()
//            }.start()
        binding.root.setOnClickListener(this)
//        val drawable : GradientDrawable =view.context.getDrawable(R.drawable.imageclip) as (GradientDrawable)
//
//
//        view.imageView.background=drawable
//        view.imageView.clipToOutline=true
    }





}