package com.example.mobdev21_night_at_the_museum.presentation.item

import com.example.mobdev21_night_at_the_museum.common.recycleview.BaseDiffUtilCallback

class ItemDiffUtil(oldData: List<Any>, newData: List<Any>) : BaseDiffUtilCallback<Any>(oldData, newData) {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = getOldItem(oldItemPosition)
        val newItem = getNewItem(newItemPosition)

        return if (oldItem is ItemAdapter.DetailInfoDisplay || newItem is ItemAdapter.DetailInfoDisplay) {
            false
        } else {
            oldItem.javaClass == newItem.javaClass
        }
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = getOldItem(oldItemPosition)
        val newItem = getNewItem(newItemPosition)

        return if (oldItem is ItemAdapter.TitleDisplay && newItem is ItemAdapter.TitleDisplay) {
            oldItem.isLike == newItem.isLike
        } else if (oldItem is ItemAdapter.DetailTitleDisplay && newItem is ItemAdapter.DetailTitleDisplay) {
            return true
        } else if (oldItem is ItemAdapter.DetailInfoDisplay && newItem is ItemAdapter.DetailInfoDisplay) {
            return oldItem.itemDetail?.key == newItem.itemDetail?.title
                    && oldItem.itemDetail?.description == newItem.itemDetail?.description
        } else {
            true
        }
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val oldItem = getOldItem(oldItemPosition)
        val newItem = getNewItem(newItemPosition)

        val list = mutableListOf<Any>()

        if (oldItem is ItemAdapter.TitleDisplay && newItem is ItemAdapter.TitleDisplay) {
            if (oldItem.isLike != newItem.isLike) {
                list.add(ItemAdapter.LIKE_PAYLOAD)
            }
        }

        if (oldItem is ItemAdapter.DetailInfoDisplay && newItem is ItemAdapter.DetailInfoDisplay) {
            if (oldItem.itemDetail?.key == newItem.itemDetail?.title
                && oldItem.itemDetail?.description == newItem.itemDetail?.description
            ) {
                list.add(ItemAdapter.DETAIL_INFO_PAYLOAD)
            }
        }

        return list.ifEmpty { null }
    }
}

