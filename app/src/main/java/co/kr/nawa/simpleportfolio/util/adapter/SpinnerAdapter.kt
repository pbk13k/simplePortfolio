package co.kr.nawa.simpleportfolio.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import co.kr.nawa.simpleportfolio.R

class SpinnerAdapter(context: Context, var items:ArrayList<String>, val layout:Int, val dropDownlayout:Int): ArrayAdapter<String>(context,layout,dropDownlayout) {


    override fun getItem(position: Int): String {
        return items.get(position)
    }

    override fun getCount(): Int {
        return items.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if (view == null) {
            view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        }
        view?.findViewById<TextView>(R.id.text)?.text = items.get(position)

        return view!!
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {

        var view = convertView
        if (view == null) {
            view = LayoutInflater.from(parent.context).inflate(dropDownlayout, parent, false)
        }
        view?.findViewById<TextView>(R.id.text)?.text = items.get(position)
        return view!!
    }



}