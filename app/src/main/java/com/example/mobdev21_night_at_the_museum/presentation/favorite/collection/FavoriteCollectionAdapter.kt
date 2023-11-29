package com.example.mobdev21_night_at_the_museum.presentation.favorite.collection

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.example.mobdev21_night_at_the_museum.R
import com.example.mobdev21_night_at_the_museum.common.extension.getAppDimensionPixel
import com.example.mobdev21_night_at_the_museum.common.extension.getAppString
import com.example.mobdev21_night_at_the_museum.common.recycleview.BaseVH
import com.example.mobdev21_night_at_the_museum.databinding.CollectionsItemBinding
import com.example.mobdev21_night_at_the_museum.databinding.ItemListTitleItemBinding
import com.example.mobdev21_night_at_the_museum.presentation.collection.all.CollectionsAdapter

class FavoriteCollectionAdapter: CollectionsAdapter() {
    companion object {
        const val HEADER_TYPE = 1410
        const val COLLECTION_TYPE = 1411
    }

    override fun getItemViewTypeCustom(position: Int): Int {
        return when (position) {
            0 -> HEADER_TYPE
            else -> COLLECTION_TYPE
        }
    }

    override fun getItemCountInRow(viewType: Int): Int {
        return when (viewType) {
            HEADER_TYPE -> 1
            else -> 2
        }
    }

    override fun getLayoutResource(viewType: Int): Int {
        return when (viewType) {
            HEADER_TYPE -> R.layout.item_list_title_item
            else -> R.layout.collections_item
        }
    }

    override fun onCreateViewHolder(viewType: Int, binding: ViewDataBinding): BaseVH<*>? {
        return when (viewType) {
            HEADER_TYPE -> TitleVH(binding as ItemListTitleItemBinding)
            else -> CollectionVH(binding as CollectionsItemBinding)
        }
    }

    inner class TitleVH(private val binding: ItemListTitleItemBinding) : BaseVH<Int>(binding) {
        override fun onBind(data: Int) {
            binding.tvItemListTitle.text = getTitleText(data)
            (binding.tvItemListTitle.layoutParams as ViewGroup.MarginLayoutParams).apply {
                marginStart = getAppDimensionPixel(R.dimen.dimen_12)
            }
        }

        private fun getTitleText(data: Int): String {
            return getAppString(R.string.collections) + " - " + data
        }
    }
}
