package co.kr.nawa.simpleportfolio.menu.view.list


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import android.widget.LinearLayout
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.lifecycle.Observer
import co.kr.nawa.simpleportfolio.R
import co.kr.nawa.simpleportfolio.databinding.ActivitySubBinding
import co.kr.nawa.simpleportfolio.databinding.ListSubItemBinding
import co.kr.nawa.simpleportfolio.holder.SubitemVIewHolder
import co.kr.nawa.simpleportfolio.item.Item
import co.kr.nawa.simpleportfolio.menu.viewModel.SubViewModel
import co.kr.nawa.simpleportfolio.util.adapter.MyAdapter
import co.kr.nawa.simpleportfolio.util.adapter.ViewHolder
import co.kr.nawa.simpleportfolio.util.basic.ActivityBase
import co.kr.nawa.simpleportfolio.util.common.logD
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.transition.platform.MaterialArcMotion

import com.google.android.material.transition.platform.MaterialContainerTransform
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import org.koin.androidx.viewmodel.ext.android.viewModel


class SubActivity : ActivityBase<ActivitySubBinding, SubViewModel>() {

    override val layoutResourceId: Int
        get() = R.layout.activity_sub
    override val viewModel: SubViewModel by viewModel()
    lateinit var bottomSheetBehavior : BottomSheetBehavior<LinearLayout>

    var onClickListener:((View, Int) -> Unit)?=null

    override fun onCreate(savedInstanceState: Bundle?) {

//        subInBinding =SubInLayoutBinding.inflate(layoutInflater)
        // Set up shared element transition
        findViewById<View>(android.R.id.content).transitionName = "shared_element_end_root"
        setEnterSharedElementCallback(MaterialContainerTransformSharedElementCallback())

        window.sharedElementEnterTransition = MaterialContainerTransform().apply {
            addTarget(android.R.id.content)
            interpolator = AccelerateDecelerateInterpolator()
            duration = 300L
        }



        window.sharedElementReturnTransition = MaterialContainerTransform().apply {
            addTarget(android.R.id.content)
            interpolator = FastOutSlowInInterpolator()
            pathMotion= MaterialArcMotion()
            duration = 300L
        }
        super.onCreate(savedInstanceState)
    }


    @SuppressLint("WrongConstant")
    private fun buildContainerTransform(entering: Boolean): MaterialContainerTransform? {

        return MaterialContainerTransform().apply {
            addTarget(android.R.id.content)
            duration = if (entering) 300 else 275
            interpolator = FastOutSlowInInterpolator()
            pathMotion = MaterialArcMotion()
            fadeMode = MaterialContainerTransform.FIT_MODE_WIDTH
            isDrawDebugEnabled = false
        }
    }


    override fun init() {

        bottomSheetBehavior=BottomSheetBehavior.from(binding.bottomLayout as LinearLayout)
        val intent=intent

        logD(
            "size=${(intent.getSerializableExtra(
                "items"
            ) as ArrayList<Item>).size}"
        )

        viewModel.items.postValue(intent.getSerializableExtra("items") as ArrayList<Item>)

        viewModel.position=intent.getIntExtra("position",-1)

//        adapter= MyAdapter(R.layout.list_sub_item, ArrayList<Item>())
//        binding.recyclerView.adapter=adapter
        object : MyAdapter(ArrayList<Item>()) {
            override fun getViewHolder(view: LayoutInflater, parent: ViewGroup): ViewHolder {

                return SubitemVIewHolder(
                    ListSubItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ), onClickListener)
            }
        }.apply {
            binding.recyclerView.adapter=this
        }



        logD("viewmode=${viewModel.items.value?.size}")
        logD("position=${viewModel.position}")
    }


    override fun init_Listener() {
        binding.back.setOnClickListener {
            finishAfterTransition()
        }
        binding.backBtn2.setOnClickListener {
            logD("bottomSheetBehavior.state=="+bottomSheetBehavior.state)
            if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED){
                bottomSheetBehavior.state=BottomSheetBehavior.STATE_COLLAPSED
            }else if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_COLLAPSED){
                bottomSheetBehavior.state=BottomSheetBehavior.STATE_EXPANDED
            }
        }
    }

    override fun init_dataBinding() {


        viewModel.items.observe(this, Observer {


            if (viewModel.position==-1 || it.size == 0){
                finishAfterTransition()
                return@Observer
            }

            val item= it[viewModel.position]
            val requestOptions = RequestOptions
                .skipMemoryCacheOf(false)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
            Glide.with(applicationContext)
                .load(item.image_url)
                .apply(requestOptions)
                .override(500, 500)
                .into(binding.bgImg)

            binding.description.text=item.description
            binding.name.text=item.name
            binding.tagline.text=item.tagline
            binding.contributedBy.text=item.contributed_by


            Glide.with(applicationContext)
                .load(item.image_url)
                .apply(requestOptions)
                .override(500, 500)
                .into(binding.bgImg2)

            binding.description2.text=item.description
            binding.name2.text=item.name
            binding.tagline2.text=item.tagline



            binding.ph.text="${item.ph}"
            binding.ebc.text="${item.ebc}"
            binding.srm.text="${item.srm}"



            val interpolator= AccelerateInterpolator()
            binding.ebcLayout.animate()
                .scaleX(1f).scaleY(1f).alpha(1f).translationY(-30f )
                .setDuration(1000).setInterpolator(interpolator).withEndAction {
                    binding.ebcLayout.animate().translationY(0f).setDuration(300).setInterpolator(interpolator).start()
                }.start()

            binding.srmLayout.animate()
                .scaleX(1f).scaleY(1f).alpha(1f).translationY(-30f )
                .setDuration(1200).setInterpolator(interpolator).withEndAction {
                    binding.srmLayout.animate().translationY(0f).setDuration(300).setInterpolator(interpolator).start()
                }.start()

            binding.phLayout.animate()
                .scaleX(1f).scaleY(1f).alpha(1f).translationY(-30f )
                .setDuration(1300).setInterpolator(interpolator).withEndAction {
                    binding.phLayout.animate().translationY(0f).setDuration(300).setInterpolator(interpolator).start()
                }.start()


            (binding.recyclerView.adapter as MyAdapter).setItems(it)

        })
    }




}
