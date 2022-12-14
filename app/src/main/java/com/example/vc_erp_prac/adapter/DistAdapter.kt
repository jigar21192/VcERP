package com.example.vc_erp_prac.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.vc_erp_prac.R
import com.example.vc_erp_prac.model.DistrictModel
import com.example.vc_erp_prac.model.StateModel

class DistAdapter (val context: Context, var dataList: List<DistrictModel>) : BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view: View
        val vh: ItemHolder
        if (convertView == null) {
            view = inflater.inflate(R.layout.custom_spinner, parent, false)
            vh = ItemHolder(view)
            view?.tag = vh
        } else {
            view = convertView
            vh = view.tag as ItemHolder
        }
        vh.label.text = dataList.get(position).districtName
        return view
    }

    override fun getItem(position: Int): Any? {
        return dataList[position];
    }

    override fun getCount(): Int {
        return dataList.size;
    }

    override fun getItemId(position: Int): Long {
        return position.toLong();
    }

    private class ItemHolder(row: View?) {
        val label: TextView

        init {
            label = row?.findViewById(R.id.tab) as TextView
        }
    }

}