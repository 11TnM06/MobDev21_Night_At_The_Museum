package com.example.mobdev21_night_at_the_museum.presentation.search

import androidx.databinding.ViewDataBinding
import com.example.mobdev21_night_at_the_museum.R
import com.example.mobdev21_night_at_the_museum.common.extension.loadImage
import com.example.mobdev21_night_at_the_museum.common.recycleview.BaseVH
import com.example.mobdev21_night_at_the_museum.databinding.CollectionsItem2Binding
import com.example.mobdev21_night_at_the_museum.domain.model.MCollection
import com.example.mobdev21_night_at_the_museum.presentation.collection.all.CollectionsAdapter

class CollectionsAdapter2: CollectionsAdapter() {
    override fun getLayoutResource(viewType: Int): Int {
        return R.layout.collections_item_2
    }

    override fun onCreateViewHolder(viewType: Int, binding: ViewDataBinding): BaseVH<*>? {
        return CollectionVH(binding as CollectionsItem2Binding)
    }

    inner class CollectionVH(val binding: CollectionsItem2Binding) : BaseVH<MCollection>(binding) {
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
}
