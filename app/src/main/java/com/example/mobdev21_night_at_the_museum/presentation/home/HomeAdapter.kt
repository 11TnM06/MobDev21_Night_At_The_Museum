package com.example.mobdev21_night_at_the_museum.presentation.home

import android.annotation.SuppressLint
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.mobdev21_night_at_the_museum.R
import com.example.mobdev21_night_at_the_museum.common.extension.loadImage
import com.example.mobdev21_night_at_the_museum.common.extension.setOnSafeClick
import com.example.mobdev21_night_at_the_museum.common.recycleview.BaseVH
import com.example.mobdev21_night_at_the_museum.common.recycleview.MuseumAdapter
import com.example.mobdev21_night_at_the_museum.databinding.HomeCollectionItemBinding
import com.example.mobdev21_night_at_the_museum.databinding.HomeItemYouMayLikeItemBinding
import com.example.mobdev21_night_at_the_museum.databinding.HomeStreetViewItemBinding
import com.example.mobdev21_night_at_the_museum.domain.model.MCollection
import com.example.mobdev21_night_at_the_museum.domain.model.StreetView
import com.example.mobdev21_night_at_the_museum.presentation.widget.COLLECTION_MODE

class HomeAdapter : MuseumAdapter() {
    companion object {
        const val STREET_VIEW_TYPE = 1410
        const val COLLECTION_TYPE = 1411
        const val ITEM_YOU_MAY_LIKE_TYPE = 1412
    }

    var listener: IListener? = null

    override fun getItemViewTypeCustom(position: Int): Int {
        return when (getDataAtPosition(position)) {
            is StreetViewDisplay -> STREET_VIEW_TYPE
            is CollectionDisplay -> COLLECTION_TYPE
            is ItemYouMayLikeDisplay -> ITEM_YOU_MAY_LIKE_TYPE
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getLayoutResource(viewType: Int): Int {
        return when (viewType) {
            STREET_VIEW_TYPE -> R.layout.home_street_view_item
            COLLECTION_TYPE -> R.layout.home_collection_item
            ITEM_YOU_MAY_LIKE_TYPE -> R.layout.home_item_you_may_like_item
            else -> throw IllegalArgumentException("getLayoutResource: viewType is invalid")
        }
    }

    override fun onCreateViewHolder(viewType: Int, binding: ViewDataBinding): BaseVH<*>? {
        return when (viewType) {
            STREET_VIEW_TYPE -> StreetViewListVH(binding as HomeStreetViewItemBinding)
            COLLECTION_TYPE -> CollectionListVH(binding as HomeCollectionItemBinding)
            ITEM_YOU_MAY_LIKE_TYPE -> ItemYouMayLikeVH(binding as HomeItemYouMayLikeItemBinding)
            else -> throw IllegalArgumentException("onCreateViewHolder: viewType is invalid")
        }
    }

    inner class StreetViewListVH(private val binding: HomeStreetViewItemBinding) : BaseVH<StreetViewDisplay>(binding) {
        private val adapter by lazy { StreetViewAdapter() }

        init {
            adapter.listener = object : StreetViewAdapter.IListener {
                override fun onStreetViewClick(streetView: StreetView) {
                    listener?.onStreetViewClick(streetView)
                }
            }
            binding.cvHomeStreetView.apply {
                setAdapter(this@StreetViewListVH.adapter)
                setLayoutManager(COLLECTION_MODE.HORIZONTAL)
            }
            binding.mcvHomeStreetViewViewAll.setOnSafeClick {
                listener?.onViewAllStreetViewClick()
            }
        }

        override fun onBind(data: StreetViewDisplay) {
            binding.cvHomeStreetView.submitList(data.streetViews)
        }
    }

    inner class CollectionListVH(private val binding: HomeCollectionItemBinding) : BaseVH<CollectionDisplay>(binding) {
        init {
            binding.mcvHomeCollectionExploreAll.setOnSafeClick {
                listener?.onViewAllCollections()
            }
            binding.ivHomeCollection1.setOnSafeClick {
                getItem {
                    listener?.onCollectionClick(it.collections?.getOrNull(0)?.key)
                }
            }
            binding.ivHomeCollection2.setOnSafeClick {
                getItem {
                    listener?.onCollectionClick(it.collections?.getOrNull(1)?.key)
                }
            }
            binding.ivHomeCollection3.setOnSafeClick {
                getItem {
                    listener?.onCollectionClick(it.collections?.getOrNull(2)?.key)
                }
            }
            binding.ivHomeCollection4.setOnSafeClick {
                getItem {
                    listener?.onCollectionClick(it.collections?.getOrNull(3)?.key)
                }
            }
            binding.ivHomeCollection5.setOnSafeClick {
                getItem {
                    listener?.onCollectionClick(it.collections?.getOrNull(4)?.key)
                }
            }
        }

        override fun onBind(data: CollectionDisplay) {
            binding.ivHomeCollection1.loadImage(data.collections?.getOrNull(0)?.icon)
            binding.ivHomeCollection2.loadImage(data.collections?.getOrNull(1)?.icon)
            binding.ivHomeCollection3.loadImage(data.collections?.getOrNull(2)?.icon)
            binding.ivHomeCollection4.loadImage(data.collections?.getOrNull(3)?.icon)
            binding.ivHomeCollection5.loadImage(data.collections?.getOrNull(4)?.icon)
        }
    }

    inner class ItemYouMayLikeVH(private val binding: HomeItemYouMayLikeItemBinding) : BaseVH<ItemYouMayLikeDisplay>(binding) {
        private val adapter by lazy { ItemYouMayLikeAdapter() }

        init {
            adapter.listener = object : ItemYouMayLikeAdapter.IListener {
                override fun onItemClick(itemId: String?) {
                    listener?.onItemClick(itemId)
                }
            }

            binding.rvCollectionsStories.apply {
                adapter = this@ItemYouMayLikeVH.adapter
                layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL)
            }
        }

        @SuppressLint("SetTextI18n")
        override fun onBind(data: ItemYouMayLikeDisplay) {
            adapter.submitList(data.items)
        }
    }

    class StreetViewDisplay {
        var streetViews: List<StreetView>? = null
    }

    class CollectionDisplay {
        var collections: List<MCollection>? = null
    }

    class ItemYouMayLikeDisplay {
        var items: List<ItemYouMayLikeAdapter.ItemsDisplay>? = null
    }

    interface IListener {
        fun onStreetViewClick(streetView: StreetView)
        fun onViewAllStreetViewClick()
        fun onViewAllCollections()
        fun onCollectionClick(collectionId: String?)
        fun onItemClick(itemId: String?)
    }
}
