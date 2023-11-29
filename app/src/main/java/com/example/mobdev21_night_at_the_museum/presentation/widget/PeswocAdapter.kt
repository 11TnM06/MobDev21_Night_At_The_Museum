package com.example.mobdev21_night_at_the_museum.presentation.widget

import com.example.mobdev21_night_at_the_museum.common.recycleview.BaseAdapter
import com.example.mobdev21_night_at_the_museum.presentation.favorite.item.ItemDisplay

abstract class PswrdAdapter : BaseAdapter() {
    companion object {
        const val SPAN_COUNT = 10E4.toInt()
    }

    fun getItemSpanSize(position: Int): Int {
        val item = getDataAtPosition(position) as? ItemDisplay
        return if (item?.countInRow == 2) {
            item.spanSize
        } else {
            SPAN_COUNT
        }
    }
}

