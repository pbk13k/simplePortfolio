package co.kr.nawa.simpleportfolio.menu.view.list


import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import android.widget.LinearLayout

import androidx.lifecycle.Observer
import co.kr.nawa.simpleportfolio.R
import co.kr.nawa.simpleportfolio.databinding.ActivitySubBinding
import co.kr.nawa.simpleportfolio.item.Item
import co.kr.nawa.simpleportfolio.util.common.logD
import co.kr.nawa.simpleportfolio.util.adapter.MyAdapter
import co.kr.nawa.simpleportfolio.util.basic.ActivityBase
import co.kr.nawa.simpleportfolio.menu.viewModel.SubViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior

import com.google.android.material.transition.MaterialContainerTransform
import com.google.android.material.transition.MaterialContainerTransformSharedElementCallback
import kotlinx.android.synthetic.main.activity_sub.*
import kotlinx.android.synthetic.main.sub_in_layout.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class SubActivity : ActivityBase<ActivitySubBinding, SubViewModel>() {

    override val layoutResourceId: Int
        get() = R.layout.activity_sub
    override val viewModel: SubViewModel by viewModel()
    lateinit var bottomSheetBehavior : BottomSheetBehavior<LinearLayout>
    lateinit var  adapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        // Set up shared element transition
        findViewById<View>(android.R.id.content).transitionName = "shared_element_end_root"
        setEnterSharedElementCallback(MaterialContainerTransformSharedElementCallback())

        window.sharedElementEnterTransition = MaterialContainerTransform(this).apply {
            addTarget(android.R.id.content)
            interpolator = AccelerateDecelerateInterpolator()
            duration = 300L
        }

        window.sharedElementReturnTransition = MaterialContainerTransform(this).apply {
            addTarget(android.R.id.content)
//            interpolator = FastOutSlowInInterpolator()
//            pathMotion=MaterialArcMotion()
            duration = 300L
        }
        super.onCreate(savedInstanceState)
    }


//    @SuppressLint("WrongConstant")
//    private fun buildContainerTransform(entering: Boolean): MaterialContainerTransform? {
//
//        return MaterialContainerTransform(this).apply {
//            addTarget(android.R.id.content)
//            duration = if (entering) 300 else 275
//            interpolator = FastOutSlowInInterpolator()
//            pathMotion = MaterialArcMotion()
//            fadeMode = MaterialContainerTransform.FIT_MODE_WIDTH
//            isDrawDebugEnabled = false
//        }
//    }


    override fun init() {

        bottomSheetBehavior=BottomSheetBehavior.from(bottom_layout as LinearLayout)
        val intent=intent

        logD(
            "size=${(intent.getSerializableExtra(
                "items"
            ) as ArrayList<Item>).size}"
        )

        viewModel.items.postValue(intent.getSerializableExtra("items") as ArrayList<Item>)

        viewModel.position=intent.getIntExtra("position",-1)

        adapter= MyAdapter(R.layout.list_sub_item, ArrayList<Item>())
        recyclerView.adapter=adapter

        logD("viewmode=${viewModel.items.value?.size}")
        logD("position=${viewModel.position}")
    }


    override fun init_Listener() {
        back.setOnClickListener {
            finishAfterTransition()
        }
    }

    override fun init_dataBinding() {


        viewModel.items.observe(this, Observer {

            logD("it.size=${it.size}")
            logD("position=${viewModel.position}")

            if (viewModel.position==-1 || it.size == 0){
                finishAfterTransition()
                return@Observer
            }

            val item=it.get(viewModel.position)
            val requestOptions = RequestOptions
                .skipMemoryCacheOf(false)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
            Glide.with(applicationContext)
                .load(item.image_url)
                .apply(requestOptions)
                .override(500, 500)
                .into(bg_img)

            description.text=item.description
            name.text=item.name
            tagline.text=item.tagline
            contributed_by.text=item.contributed_by


            Glide.with(applicationContext)
                .load(item.image_url)
                .apply(requestOptions)
                .override(500, 500)
                .into(bg_img2)

            description2.text=item.description
            name2.text=item.name
            tagline2.text=item.tagline



            ph.text="${item.ph}"
            ebc.text="${item.ebc}"
            srm.text="${item.srm}"



            val interpolator= AccelerateInterpolator()
            ebc_layout.animate()
                .scaleX(1f).scaleY(1f).alpha(1f).translationY(-30f )
                .setDuration(1000).setInterpolator(interpolator).withEndAction {
                    ebc_layout.animate().translationY(0f).setDuration(300).setInterpolator(interpolator).start()
                }.start()

            srm_layout.animate()
                .scaleX(1f).scaleY(1f).alpha(1f).translationY(-30f )
                .setDuration(1200).setInterpolator(interpolator).withEndAction {
                    srm_layout.animate().translationY(0f).setDuration(300).setInterpolator(interpolator).start()
                }.start()

            ph_layout.animate()
                .scaleX(1f).scaleY(1f).alpha(1f).translationY(-30f )
                .setDuration(1300).setInterpolator(interpolator).withEndAction {
                    ph_layout.animate().translationY(0f).setDuration(300).setInterpolator(interpolator).start()
                }.start()




            adapter.setItems(it)

        })
    }




}
