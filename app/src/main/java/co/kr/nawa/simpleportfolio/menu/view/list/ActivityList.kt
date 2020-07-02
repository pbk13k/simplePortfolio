package co.kr.nawa.simpleportfolio.menu.view.list


import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.lifecycle.Observer
import co.kr.nawa.simpleportfolio.R

import co.kr.nawa.simpleportfolio.databinding.ActivityListBinding
import co.kr.nawa.simpleportfolio.util.common.logD
import co.kr.nawa.simpleportfolio.menu.viewModel.ListViewModel
import co.kr.nawa.simpleportfolio.util.adapter.MyAdapter
import co.kr.nawa.simpleportfolio.util.basic.ActivityBase
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout
import com.google.android.material.transition.MaterialContainerTransformSharedElementCallback
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.tab_layout.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ActivityList : ActivityBase<ActivityListBinding,ListViewModel>() {


    override val layoutResourceId: Int
        get() = R.layout.activity_list
    override val viewModel: ListViewModel by viewModel()
    private var names=ArrayList<String>()
    lateinit var  adapter: MyAdapter
    private var menuWidth=0
    private var menuSpace=0
    private var menu1percent=0f
    private var addLength=0
    private var menuLayoutPadding=0


    override fun onCreate(savedInstanceState: Bundle?) {
        // Enable Activity Transitions. Optionally enable Activity transitions in your
        // theme with <item name=”android:windowActivityTransitions”>true</item>.
        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)

        // Attach a callback used to capture the shared elements from this Activity to be used
        // by the container transform transition
        setExitSharedElementCallback(MaterialContainerTransformSharedElementCallback())

        // Keep system bars (status bar, navigation bar) persistent throughout the transition.
        window.sharedElementsUseOverlay = false
        super.onCreate(savedInstanceState)
    }


    override fun init() {

        adapter= viewModel.items.value?.let { MyAdapter(R.layout.list_item, it) }!!
        recyclerView.adapter=adapter
        recyclerView1.adapter=adapter
        menu_layout.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)

        names.add("page1")
        names.add("page2")
        tab()

        viewModel.itemsGet(1)

        val point = Point()
        (getSystemService(WINDOW_SERVICE) as WindowManager).defaultDisplay.getSize(point)
        val height = point.y

        recyclerView1.layoutParams.height=height

    }




    override fun init_Listener() {

        app_bar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            val barTotal=appBarLayout.totalScrollRange
            val bar1percent=barTotal.toFloat()/100
            val scoll= verticalOffset * -1
            val barpercent=scoll / bar1percent

            val addLeft= (barpercent * menu1percent).toInt() +menuWidth

            if (barTotal != scoll){
                menu_layout.setBackgroundResource(R.drawable.bg_black_lt_r)
                menuWidth(addLeft)
            }else{
                menuWidth(menuSpace+menuWidth)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    menu_layout.setBackgroundColor(getColor(R.color.black))
                }else{
                    menu_layout.setBackgroundColor(resources.getColor(R.color.black))
                }

            }

        })

        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab) {}

            override fun onTabUnselected(tab: TabLayout.Tab) {}

            override fun onTabSelected(tab: TabLayout.Tab) {
                viewModel.itemsGet(tab.position+1)
            }
        })
        adapter.onClickListener={view, position ->
            val intent= Intent(applicationContext, SubActivity::class.java)
            val options = ActivityOptions.makeSceneTransitionAnimation(
                this@ActivityList, view, "shared_element_end_root"
            )
            intent.putExtra("item",viewModel.items.value?.get(position))
            intent.putExtra("items",viewModel.items.value)
            intent.putExtra("position",position)
            startActivity(intent,options.toBundle())
        }

    }

    fun menuWidth(add_l:Int){

//        logD("tab_s=${(add_l -menu_layout_padding)/ names.size}")
        val tab_width=(add_l -menuLayoutPadding)/ names.size
        for (i in 0 until tabs.tabCount){

            tabs.getTabAt(i)?.customView?.layoutParams?.width=tab_width
        }

        val param=menu_layout.layoutParams
        param.width=add_l
        if (add_l!=addLength){
            menu_layout.layoutParams=param
            addLength=add_l
        }
    }




    override fun init_dataBinding() {

        viewModel.items.observe(this, Observer {
            if(it.size>0){
                Glide.with(applicationContext).load(it.get(0).image_url).into(mainimg)
                name.text=it.get(0).contributed_by
                tagline.text=it.get(0).tagline

            }
            adapter.setItems(it)
            logD("items=${it.size}")
        })


    }

    private fun tab() {

        menuLayoutPadding=menu_layout.measuredWidth

        for (i in names.indices) {
            val tab = tabs.newTab()
            tabs.addTab(tab)
            val tabView = layoutInflater.inflate(R.layout.tab_layout, null)
            tabView.tab_text.text = names[i]

            tabs.getTabAt(i)!!.customView = tabView
            tabView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)

            menuWidth += tabView.measuredWidth
            logD("tab_aa=${tabView.measuredWidth}")
        }

        val tabView = tabs.getTabAt(0)!!.customView
        tabView!!.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        val tabsparam=tabs.layoutParams
        tabsparam.height = tabView.measuredHeight
//        tabsparam.width = tabView.measuredWidth * names.size
        tabs.layoutParams=tabsparam

        menuWidth += menuLayoutPadding
        val point = Point()
        (getSystemService(WINDOW_SERVICE) as WindowManager).defaultDisplay.getSize(point)
        val width = point.x
        menuSpace = width-menuWidth
        menu1percent=menuSpace.toFloat()/100

    }


}
