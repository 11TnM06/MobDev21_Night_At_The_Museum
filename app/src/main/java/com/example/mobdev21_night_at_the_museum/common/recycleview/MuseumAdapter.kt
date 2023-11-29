package com.example.mobdev21_night_at_the_museum.common.recycleview

import com.example.mobdev21_night_at_the_museum.presentation.widget.BaseGridAdapter

abstract class MuseumAdapter : BaseAdapter() {
    fun <DATA> BaseVH<DATA>.getItem(onAction: (DATA) -> Unit) {
        val item = getDataAtPosition(absoluteAdapterPosition) as? DATA
        item?.let {
            onAction.invoke(it)
        }
    }
}

abstract class GridMuseumAdapter : BaseGridAdapter() {
    fun <DATA> BaseVH<DATA>.getItem(onAction: (DATA) -> Unit) {
        val item = getDataAtPosition(absoluteAdapterPosition) as? DATA
        item?.let {
            onAction.invoke(it)
        }
    }
}

