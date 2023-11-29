package com.example.mobdev21_night_at_the_museum.presentation.item

import androidx.databinding.ViewDataBinding
import com.example.mobdev21_night_at_the_museum.R
import com.example.mobdev21_night_at_the_museum.common.extension.loadImage
import com.example.mobdev21_night_at_the_museum.common.extension.setOnSafeClick
import com.example.mobdev21_night_at_the_museum.common.recycleview.BaseVH
import com.example.mobdev21_night_at_the_museum.common.recycleview.MuseumAdapter
import com.example.mobdev21_night_at_the_museum.databinding.ItemRecommendSubItemBinding
import com.example.mobdev21_night_at_the_museum.domain.model.Item

class ItemRecommendAdapter: MuseumAdapter() {
    var listener: IListener? = null

    override fun getLayoutResource(viewType: Int): Int {
        return R.layout.item_recommend_sub_item
    }

    override fun onCreateViewHolder(viewType: Int, binding: ViewDataBinding): BaseVH<*>? {
        return ItemVH(binding as ItemRecommendSubItemBinding)
    }

    inner class ItemVH(private val binding: ItemRecommendSubItemBinding) : BaseVH<Item>(binding) {
        init {
            binding.root.setOnSafeClick { getItem { listener?.onClickItem(it.key) } }
        }

        override fun onBind(data: Item) {
            binding.tvItemRecommendSubName.text = data.name
            binding.ivItemRecommendSub.loadImage(data.thumbnail)
        }
    }

    interface IListener {
        fun onClickItem(itemId: String?)
    }
}
