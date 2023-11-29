package com.example.mobdev21_night_at_the_museum.presentation.collection

import androidx.databinding.ViewDataBinding
import com.example.mobdev21_night_at_the_museum.R
import com.example.mobdev21_night_at_the_museum.common.extension.loadImage
import com.example.mobdev21_night_at_the_museum.common.recycleview.BaseVH
import com.example.mobdev21_night_at_the_museum.common.recycleview.MuseumAdapter
import com.example.mobdev21_night_at_the_museum.databinding.CollectionItemsSubItemBinding

class CollectionItemAdapter : MuseumAdapter() {
    var listener: IListener? = null

    override fun getLayoutResource(viewType: Int): Int {
        return R.layout.collection_items_sub_item
    }

    override fun onCreateViewHolder(viewType: Int, binding: ViewDataBinding): BaseVH<*>? {
        return ItemVH(binding as CollectionItemsSubItemBinding)
    }

    inner class ItemVH(private val binding: CollectionItemsSubItemBinding) : BaseVH<ItemsDisplay>(binding) {
        init {
            binding.root.setOnClickListener { getItem { listener?.onItemClick(it.id) } }
        }

        override fun onBind(data: ItemsDisplay) {
            val params = binding.ivCollectionItemsSub.layoutParams
            binding.ivCollectionItemsSub.layoutParams = params.apply {
                width = (data.ratio * height).toInt()
            }
            binding.ivCollectionItemsSub.loadImage(data.thumbnail)
        }
    }

    data class ItemsDisplay(
        var id: String? = null,
        var thumbnail: String? = null,
        var ratio: Float = 1f
    )

    interface IListener {
        fun onItemClick(itemId: String?)
    }
}
