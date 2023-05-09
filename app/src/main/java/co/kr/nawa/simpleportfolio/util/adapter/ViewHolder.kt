package co.kr.nawa.simpleportfolio.util.adapter


import android.view.View
import androidx.recyclerview.widget.RecyclerView




abstract class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun init(item: Any)


}