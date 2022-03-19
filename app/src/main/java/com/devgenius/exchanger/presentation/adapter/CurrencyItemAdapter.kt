package com.devgenius.exchanger.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.devgenius.exchanger.domain.entity.Rate

internal class CurrencyItemAdapter(
   private var rateList: List<Rate>
) : RecyclerView.Adapter<RateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RateViewHolder {
        return RateViewHolder.from(parent)
    }

    override fun getItemCount(): Int = rateList.size

    override fun onBindViewHolder(holder: RateViewHolder, position: Int) {
        val rate = rateList[position]
        holder.bind(rate)
    }

    fun setList(list: List<Rate>){
        val diffUtil = RateItemDiffUtilCallback(rateList, list)
        val differResult = DiffUtil.calculateDiff(diffUtil)
        this.rateList = list
        differResult.dispatchUpdatesTo(this)
    }


}