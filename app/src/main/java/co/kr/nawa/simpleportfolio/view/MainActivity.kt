package co.kr.nawa.simpleportfolio.view


import android.content.Intent
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import co.kr.nawa.simpleportfolio.R
import co.kr.nawa.simpleportfolio.databinding.ActivityMainBinding
import co.kr.nawa.simpleportfolio.util.common.logD
import co.kr.nawa.simpleportfolio.util.adapter.MyAdapter
import co.kr.nawa.simpleportfolio.util.basic.ActivityBase
import co.kr.nawa.simpleportfolio.viewModel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : ActivityBase<ActivityMainBinding, MainViewModel>() {

    override val layoutResourceId: Int
        get() = R.layout.activity_main
    override val viewModel: MainViewModel by viewModel()
    lateinit var adapter:MyAdapter

    override fun init() {
//        toolbar.title="Simple Project"


        adapter=MyAdapter(R.layout.list_menu, ArrayList())
        list.adapter=adapter
        list.addItemDecoration( DividerItemDecoration(list.context, DividerItemDecoration.VERTICAL))
        list.addItemDecoration( DividerItemDecoration(list.context, DividerItemDecoration.HORIZONTAL))

    }



    override fun init_Listener() {
        adapter.onClickListener={ _, position ->
            val items=viewModel.items.value
            items?.let {
                if (it.size>0){
                    startActivity(Intent(this@MainActivity, it[position].cls))
                }
            }
        }
    }

    override fun init_dataBinding() {
        viewModel.items.observe(this, Observer {
            logD("ItemVIewHolder-=${it.size}")

            adapter.setItems(it)
        })

    }


}
