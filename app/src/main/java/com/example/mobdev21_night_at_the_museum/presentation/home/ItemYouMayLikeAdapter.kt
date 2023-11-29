package com.example.mobdev21_night_at_the_museum.presentation.home

import androidx.databinding.ViewDataBinding
import com.example.mobdev21_night_at_the_museum.R
import com.example.mobdev21_night_at_the_museum.common.extension.loadImage
import com.example.mobdev21_night_at_the_museum.common.extension.setOnSafeClick
import com.example.mobdev21_night_at_the_museum.common.recycleview.BaseVH
import com.example.mobdev21_night_at_the_museum.common.recycleview.MuseumAdapter
import com.example.mobdev21_night_at_the_museum.databinding.ItemYouMayLikeItemBinding
import com.example.mobdev21_night_at_the_museum.domain.model.Item
import com.example.mobdev21_night_at_the_museum.domain.usecase.updateLikeItem

class ItemYouMayLikeAdapter : MuseumAdapter() {
    var listener: IListener? = null

    override fun getLayoutResource(viewType: Int): Int {
        return R.layout.item_you_may_like_item
    }

    override fun onCreateViewHolder(viewType: Int, binding: ViewDataBinding): BaseVH<*>? {
        return ItemVH(binding as ItemYouMayLikeItemBinding)
    }

    inner class ItemVH(private val binding: ItemYouMayLikeItemBinding) : BaseVH<ItemsDisplay>(binding) {
        init {
            binding.ivCollectionItemsSub.setOnClickListener { getItem { listener?.onItemClick(it.item?.key) } }
            binding.ivItemYouMayLike.setOnSafeClick {
                getItem {
                    val isLike = it.item?.safeIsLiked() ?: false
                    updateLikeItem(isLike, it.item?.key, onSuccessAction = {
                        it.item?.mapIsLiked()
                        updateLikeStatus(it)
                    }, onFailureAction = {})
                }
            }
        }

        override fun onBind(data: ItemsDisplay) {
            val params = binding.ivCollectionItemsSub.layoutParams
            binding.ivCollectionItemsSub.layoutParams = params.apply {
                width = (data.ratio * height).toInt()
            }
            binding.ivCollectionItemsSub.loadImage(data.item?.thumbnail)
            updateLikeStatus(data)
        }

        private fun updateLikeStatus(data: ItemsDisplay) {
            if (data.item?.safeIsLiked() == true) {
                binding.ivItemYouMayLike.setImageResource(R.drawable.ic_like_filled)
            } else {
                binding.ivItemYouMayLike.setImageResource(R.drawable.ic_like)
            }
        }
    }

    data class ItemsDisplay(
        var item: Item? = null,
        var ratio: Float = 1f
    )

    interface IListener {
        fun onItemClick(itemId: String?)
    }
}
