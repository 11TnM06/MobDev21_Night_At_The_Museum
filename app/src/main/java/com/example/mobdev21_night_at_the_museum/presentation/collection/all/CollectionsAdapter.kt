package com.example.mobdev21_night_at_the_museum.presentation.collection.all

import androidx.databinding.ViewDataBinding
import com.example.mobdev21_night_at_the_museum.R
import com.example.mobdev21_night_at_the_museum.common.extension.loadImage
import com.example.mobdev21_night_at_the_museum.common.recycleview.BaseVH
import com.example.mobdev21_night_at_the_museum.common.recycleview.GridMuseumAdapter
import com.example.mobdev21_night_at_the_museum.databinding.CollectionsItemBinding
import com.example.mobdev21_night_at_the_museum.domain.model.MCollection

open class CollectionsAdapter : GridMuseumAdapter() {
    var listener: IListener? = null

    override fun setupEmptyState() = Empty(overrideLayoutRes = R.layout.collections_empty_item)

    override fun getItemCountInRow(viewType: Int) = 2

    override fun getLayoutResource(viewType: Int) = R.layout.collections_item

    override fun onCreateViewHolder(viewType: Int, binding: ViewDataBinding): BaseVH<*>? {
        return CollectionVH(binding as CollectionsItemBinding)
    }

    inner class CollectionVH(val binding: CollectionsItemBinding) : BaseVH<MCollection>(binding) {
        init {
            binding.constCollectionsRoot.setOnClickListener {
                getItem { currentItem ->
                    listener?.onCollectionClick(currentItem)
                }
            }
        }

        override fun onBind(data: MCollection) {
            binding.apply {
                binding.ivCollectionsIcon.loadImage(data.icon)
                binding.ivCollectionsThumbnail.loadImage(data.thumbnail)
                binding.tvCollectionsName.text = data.name
                binding.tvCollectionsPlace.text = data.place
            }
        }
    }

    interface IListener {
        fun onCollectionClick(collection: MCollection)
    }
}
