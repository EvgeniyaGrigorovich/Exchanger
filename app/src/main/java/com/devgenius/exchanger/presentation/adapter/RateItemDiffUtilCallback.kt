package com.devgenius.exchanger.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.devgenius.exchanger.domain.entity.Rate

/**
 * DiffUtilCallback
 *
 * @constructor
 * @param oldList список со старыми значениями
 * @param newList список с новыми значениями
 *
 * @author Evgeniia Grigorovcj
 */
internal class RateItemDiffUtilCallback(
    private val oldList: List<Rate>,
    private val newList: List<Rate>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].currency == newList[newItemPosition].currency
                && oldList[oldItemPosition].value == newList[newItemPosition].value
    }
}