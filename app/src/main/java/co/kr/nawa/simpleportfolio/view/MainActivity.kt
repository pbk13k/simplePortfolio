package co.kr.nawa.simpleportfolio.view



import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import co.kr.nawa.simpleportfolio.R
import co.kr.nawa.simpleportfolio.databinding.ActivityMainBinding
import co.kr.nawa.simpleportfolio.databinding.ListMenuBinding
import co.kr.nawa.simpleportfolio.holder.MenuVIewHolder
import co.kr.nawa.simpleportfolio.util.adapter.MyAdapter
import co.kr.nawa.simpleportfolio.util.adapter.ViewHolder
import co.kr.nawa.simpleportfolio.util.basic.ActivityBase
import co.kr.nawa.simpleportfolio.viewModel.MainViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : ActivityBase<ActivityMainBinding, MainViewModel>() {

    override val layoutResourceId: Int
        get() = R.layout.activity_main
    override val viewModel: MainViewModel by viewModel()


    override fun init() {
//        toolbar.title="Simple Project"


        object : MyAdapter(ArrayList()) {
            override fun getViewHolder(view: LayoutInflater, parent: ViewGroup): ViewHolder {
                return MenuVIewHolder(
                    ListMenuBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                ) { _, position ->
                    val items = viewModel.items.value
                    items?.let {
                        if (it.size > 0) {
                            startActivity(Intent(this@MainActivity, it[position].cls))
                        }
                    }
                }
            }
        }.apply {
            binding.list.adapter=this
            binding.list.addItemDecoration( DividerItemDecoration(baseContext, DividerItemDecoration.VERTICAL))
            binding.list.addItemDecoration( DividerItemDecoration(baseContext, DividerItemDecoration.HORIZONTAL))
        }


    }



    override fun init_Listener() {

    }

    override fun init_dataBinding() {
        viewModel.items.observe(this, Observer {
            (binding.list.adapter as MyAdapter).setItems(it)
        })

    }

}
