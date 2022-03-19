package com.devgenius.exchanger.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devgenius.exchanger.databinding.RateItemBinding
import com.devgenius.exchanger.domain.entity.Rate


internal class RateViewHolder(
    private val binding: RateItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(rate: Rate) {
        binding.currencyName.text = rate.currency
        binding.currencyPrice.text = rate.value.toString()
    }

    companion object {
        fun from(parent: ViewGroup): RateViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = RateItemBinding.inflate(
                layoutInflater,
                parent,
                false)

            return RateViewHolder(binding)
        }
    }

}