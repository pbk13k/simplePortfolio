package co.kr.nawa.simpleportfolio.holder

import android.view.View
import co.kr.nawa.simpleportfolio.databinding.ListItemBinding
import co.kr.nawa.simpleportfolio.item.Item
import co.kr.nawa.simpleportfolio.util.adapter.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.extensions.LayoutContainer


class ItemVIewHolder(private val binding: ListItemBinding, private var mListener: ((View, Int)->Unit)?):
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
            .into(binding.imageView)
        binding.textView.text=i.name


        binding.root.setOnClickListener(this)
//        val drawable : GradientDrawable =view.context.getDrawable(R.drawable.imageclip) as (GradientDrawable)
//
//
//        view.imageView.background=drawable
//        view.imageView.clipToOutline=true
    }


}