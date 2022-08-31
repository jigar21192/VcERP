package com.example.vc_erp_prac.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.vc_erp_prac.R
import com.example.vc_erp_prac.databinding.RowDataBinding
import com.example.vc_erp_prac.model.ContactModel

class DataAdapter(
    private val context: Context,
    private val modelList: ArrayList<ContactModel>,
    private val listener:OnClickListener
) : RecyclerView.Adapter<DataAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_data, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var model=modelList.get(position)
        holder.binding?.apply {
            tvName.text=model.firstName+" "+model.lastName
            tvMobile.text=model.mobileNumber
        }

        holder.binding?.ivDelete?.setOnClickListener {
            listener.onDeleteClick(holder.adapterPosition)
        }

    }

    override fun getItemCount(): Int {
        return modelList.size
    }

    inner class MyViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        var binding: RowDataBinding? = DataBindingUtil.bind(itemView!!)
    }

    interface OnClickListener{
        fun onDeleteClick(position: Int)
    }
}