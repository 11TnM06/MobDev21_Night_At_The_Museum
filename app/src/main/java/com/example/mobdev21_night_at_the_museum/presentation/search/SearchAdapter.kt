package com.example.mobdev21_night_at_the_museum.presentation.search

import android.annotation.SuppressLint
import androidx.databinding.ViewDataBinding
import com.example.mobdev21_night_at_the_museum.R
import com.example.mobdev21_night_at_the_museum.common.recycleview.BaseVH
import com.example.mobdev21_night_at_the_museum.common.recycleview.MuseumAdapter
import com.example.mobdev21_night_at_the_museum.databinding.SearchCollectionItemBinding
import com.example.mobdev21_night_at_the_museum.databinding.SearchItemItemBinding
import com.example.mobdev21_night_at_the_museum.databinding.SearchStoryItemBinding
import com.example.mobdev21_night_at_the_museum.domain.model.Item
import com.example.mobdev21_night_at_the_museum.domain.model.MCollection
import com.example.mobdev21_night_at_the_museum.domain.model.Story
import com.example.mobdev21_night_at_the_museum.presentation.collection.all.CollectionsAdapter
import com.example.mobdev21_night_at_the_museum.presentation.favorite.FavoriteStoryAdapter
import com.example.mobdev21_night_at_the_museum.presentation.widget.COLLECTION_MODE

class SearchAdapter : MuseumAdapter() {
    companion object {
        const val ITEM_VIEW_TYPE = 1410
        const val STORY_VIEW_TYPE = 1411
        const val COLLECTION_VIEW_TYPE = 1412
    }

    var listener: IListener? = null

    override fun setupEmptyState(): Empty {
        return Empty(overrideLayoutRes = R.layout.collections_empty_item)
    }

    override fun getItemViewTypeCustom(position: Int): Int {
        return when (getDataAtPosition(position)) {
            is SearchItemDisplay -> ITEM_VIEW_TYPE
            is SearchStoryDisplay -> STORY_VIEW_TYPE
            is SearchCollectionDisplay -> COLLECTION_VIEW_TYPE
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getLayoutResource(viewType: Int): Int {
        return when (viewType) {
            ITEM_VIEW_TYPE -> R.layout.search_item_item
            STORY_VIEW_TYPE -> R.layout.search_story_item
            COLLECTION_VIEW_TYPE -> R.layout.search_collection_item
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onCreateViewHolder(viewType: Int, binding: ViewDataBinding): BaseVH<*>? {
        return when (viewType) {
            ITEM_VIEW_TYPE -> SearchItemVH(binding as SearchItemItemBinding)
            STORY_VIEW_TYPE -> SearchStoryVH(binding as SearchStoryItemBinding)
            COLLECTION_VIEW_TYPE -> SearchCollectionVH(binding as SearchCollectionItemBinding)
            else -> throw IllegalArgumentException("onCreateViewHolder: viewType is invalid")
        }
    }

    inner class SearchItemVH(private val binding: SearchItemItemBinding) : BaseVH<SearchItemDisplay>(binding) {
        private val adapter by lazy { SearchItemSubAdapter() }

        init {
            adapter.listener = object : SearchItemSubAdapter.IListener {
                override fun onItemClick(itemId: String?) {
                    listener?.onItemClick(itemId)
                }
            }

            binding.cvSearchItem.apply {
                setAdapter(this@SearchItemVH.adapter)
                setLayoutManager(COLLECTION_MODE.HORIZONTAL)
            }
        }

        @SuppressLint("SetTextI18n")
        override fun onBind(data: SearchItemDisplay) {
            binding.cvSearchItem.submitList(data.items)
        }
    }

    inner class SearchStoryVH(private val binding: SearchStoryItemBinding) : BaseVH<SearchStoryDisplay>(binding) {
        private val adapter by lazy { FavoriteStoryAdapter() }

        init {
            adapter.listener = object : FavoriteStoryAdapter.IListener {
                override fun onStoryClick(storyId: String?) {
                    listener?.onStoryClick(storyId)
                }
            }

            binding.cvSearchStoryItem.apply {
                setAdapter(this@SearchStoryVH.adapter)
                setLayoutManager(COLLECTION_MODE.HORIZONTAL)
            }
        }

        @SuppressLint("SetTextI18n")
        override fun onBind(data: SearchStoryDisplay) {
            binding.cvSearchStoryItem.submitList(data.stories)
        }
    }

    inner class SearchCollectionVH(private val binding: SearchCollectionItemBinding) : BaseVH<SearchCollectionDisplay>(binding) {
        private val adapter by lazy { CollectionsAdapter2() }

        init {
            adapter.listener = object : CollectionsAdapter.IListener {
                override fun onCollectionClick(collection: MCollection) {
                    listener?.onCollectionClick(collection.key)
                }
            }

            binding.cvSearchCollectionItem.apply {
                setAdapter(this@SearchCollectionVH.adapter)
                setLayoutManager(COLLECTION_MODE.HORIZONTAL)
            }
        }

        @SuppressLint("SetTextI18n")
        override fun onBind(data: SearchCollectionDisplay) {
            binding.cvSearchCollectionItem.submitList(data.collections)
        }
    }

    data class SearchItemDisplay(
        var items: List<Item>? = null
    )

    data class SearchCollectionDisplay(
        var collections: List<MCollection>? = null
    )

    data class SearchStoryDisplay(
        var stories: List<Story>? = null
    )

    interface IListener {
        fun onItemClick(itemId: String?)
        fun onCollectionClick(collectionId: String?)
        fun onStoryClick(storyId: String?)
    }
}
