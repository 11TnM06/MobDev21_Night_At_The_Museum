package com.example.mobdev21_night_at_the_museum.presentation.download

import com.example.mobdev21_night_at_the_museum.common.recycleview.BaseDiffUtilCallback

class DownloadDiffUtil(oldData: List<Any>, newData: List<Any>) : BaseDiffUtilCallback<Any>(oldData, newData) {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = getOldItem(oldItemPosition) as DownloadAdapter.DownloadItem
        val newItem = getNewItem(newItemPosition) as DownloadAdapter.DownloadItem

        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return true
    }
}

