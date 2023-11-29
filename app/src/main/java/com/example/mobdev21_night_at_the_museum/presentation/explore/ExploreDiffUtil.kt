package com.example.mobdev21_night_at_the_museum.presentation.explore

import com.example.mobdev21_night_at_the_museum.common.recycleview.BaseDiffUtilCallback
import com.example.mobdev21_night_at_the_museum.domain.model.Exhibition
import com.example.mobdev21_night_at_the_museum.domain.model.Item

class ExploreDiffUtil(oldData: List<Any>, newData: List<Any>) : BaseDiffUtilCallback<Any>(oldData, newData) {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = getOldItem(oldItemPosition)
        val newItem = getNewItem(newItemPosition)

        return if (oldItem is Item && newItem is Item) {
            oldItem.key == newItem.key
        } else if (oldItem is Exhibition && newItem is Exhibition) {
            oldItem.key == newItem.key
        } else {
            oldItem.hashCode() == newItem.hashCode()
        }
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = getOldItem(oldItemPosition)
        val newItem = getNewItem(newItemPosition)

        return if (oldItem is Item && newItem is Item) {
            oldItem.safeIsLiked() == newItem.safeIsLiked()
//                    && oldItem.collection?.name == newItem.collection?.name
//                    && oldItem.collection?.thumbnail == newItem.collection?.thumbnail
//                    && oldItem.thumbnail == newItem.thumbnail
//                    && oldItem.name == newItem.name
//                    && oldItem.creator?.name == newItem.creator?.name
        } else {
            true
        }
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val oldItem = getOldItem(oldItemPosition)
        val newItem = getNewItem(newItemPosition)

        val list = mutableListOf<Any>()

        if (oldItem is Item && newItem is Item) {
            if (oldItem.safeIsLiked() != newItem.safeIsLiked()) list.add(ExploreAdapter.LIKE_PAYLOAD)
        }

        return list.ifEmpty { null }
    }
}

