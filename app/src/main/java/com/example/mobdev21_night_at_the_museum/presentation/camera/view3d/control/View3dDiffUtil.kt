package com.example.mobdev21_night_at_the_museum.presentation.camera.view3d.control

import com.example.mobdev21_night_at_the_museum.common.recycleview.BaseDiffUtilCallback

class View3dDiffUtil(oldData: List<Any>, newData: List<Any>) : BaseDiffUtilCallback<Any>(oldData, newData) {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = getOldItem(oldItemPosition) as View3dControllerAdapter.ItemDisplay
        val newItem = getNewItem(newItemPosition) as View3dControllerAdapter.ItemDisplay

        return oldItem.item.key == newItem.item.key
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = getOldItem(oldItemPosition) as View3dControllerAdapter.ItemDisplay
        val newItem = getNewItem(newItemPosition) as View3dControllerAdapter.ItemDisplay

        return oldItem.isSelected == newItem.isSelected
                && oldItem.downloadStatus == newItem.downloadStatus
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val oldItem = getOldItem(oldItemPosition) as View3dControllerAdapter.ItemDisplay
        val newItem = getNewItem(newItemPosition) as View3dControllerAdapter.ItemDisplay

        val list = mutableListOf<Any>()

        if (oldItem.isSelected != newItem.isSelected) list.add(View3dControllerAdapter.SELECT_PAYLOAD)
        if (oldItem.downloadStatus != newItem.downloadStatus) list.add(View3dControllerAdapter.DOWNLOAD_PAYLOAD)

        return list.ifEmpty { null }
    }
}

