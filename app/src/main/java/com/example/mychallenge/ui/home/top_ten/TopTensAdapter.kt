package com.example.mychallenge.ui.home.top_ten

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mychallenge.R
import com.example.mychallenge.common.Keys.Companion.TYPE_HEADER_INCREASE_ID
import com.example.mychallenge.common.Keys.Companion.TYPE_HEADER_LOW_ID
import com.example.mychallenge.common.Keys.Companion.TYPE_HEADER_VOLUME_ID
import com.example.mychallenge.databinding.AdapterListToptenBinding
import com.example.mychallenge.databinding.HeaderListToptenBinding
import com.example.mychallenge.domain.module.TopTens
import java.text.DecimalFormat

class TopTensAdapter(private var listTopTenByGroup: List<Any>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == 1){
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.adapter_list_topten, parent, false)
            return ChildViewHolder(view)

        }else{
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.header_list_topten, parent, false)
            return HeaderViewHolder(view)
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ChildViewHolder){
            holder.bind(listTopTenByGroup[position] as TopTens)
        }else if(holder is HeaderViewHolder){
            holder.headerBind(listTopTenByGroup[position] as Int)
        }

    }

    override fun getItemViewType(position: Int): Int {
        if(listTopTenByGroup[position] is TopTens)
            return 1
        else
            return 0
    }

    override fun getItemCount(): Int {
        return listTopTenByGroup.size
    }

    fun setUpdateData(listTopTens: List<Any>){
        listTopTenByGroup = listTopTens
        notifyDataSetChanged()
    }

    inner class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = HeaderListToptenBinding.bind(view)

        fun headerBind(type: Int){
            binding.issueId.text = when(type){
                TYPE_HEADER_INCREASE_ID -> "Alzas"
                TYPE_HEADER_VOLUME_ID -> "Volumen"
                TYPE_HEADER_LOW_ID -> "Bajas"
                else -> ""
            }

        }
    }

    inner class ChildViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = AdapterListToptenBinding.bind(view)

        fun bind(topTen: TopTens){
            val percentage = DecimalFormat("#.00").format(topTen.percentageChange)
            binding.issueId.text = topTen.issueId
            binding.lastPrice.text = topTen.lastPrice.toString()
            binding.percent.text = percentage

            if(percentage.toDouble() < 0)
                binding.percent.setTextColor(Color.RED)
            else if(percentage.toDouble() > 0)
                binding.percent.setTextColor(Color.GREEN)

        }

    }

}