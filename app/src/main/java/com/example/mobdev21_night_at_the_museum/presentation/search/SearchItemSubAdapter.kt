package com.example.mobdev21_night_at_the_museum.presentation.search

import android.graphics.drawable.Drawable
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.mobdev21_night_at_the_museum.R
import com.example.mobdev21_night_at_the_museum.common.extension.getAppDimension
import com.example.mobdev21_night_at_the_museum.common.extension.loadImage
import com.example.mobdev21_night_at_the_museum.common.recycleview.BaseVH
import com.example.mobdev21_night_at_the_museum.common.recycleview.MuseumAdapter
import com.example.mobdev21_night_at_the_museum.databinding.SearchItemSubItemBinding
import com.example.mobdev21_night_at_the_museum.domain.model.Item

class SearchItemSubAdapter : MuseumAdapter() {
    var listener: IListener? = null

    override fun getLayoutResource(viewType: Int): Int {
        return R.layout.search_item_sub_item
    }

    override fun onCreateViewHolder(viewType: Int, binding: ViewDataBinding): BaseVH<*>? {
        return ItemVH(binding as SearchItemSubItemBinding)
    }

    inner class ItemVH(private val binding: SearchItemSubItemBinding) : BaseVH<Item>(binding) {
        init {
            binding.root.setOnClickListener { getItem { listener?.onItemClick(it.key) } }
        }

        override fun onBind(data: Item) {
            binding.tvSearchItemSubTitle.text = data.name
            binding.ivSearchItemsSub.loadImage(data.thumbnail)
            try {
                Glide.with(binding.ivSearchItemsSub)
                    .load(data.thumbnail)
                    .skipMemoryCache(false)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                            return false
                        }

                        override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                            if (resource != null) {
                                updateWidth(resource.intrinsicWidth / resource.intrinsicHeight.toFloat())
                            }
                            return false
                        }
                    }).into(binding.ivSearchItemsSub)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        private fun updateWidth(ratio: Float) {
            binding.vSearchItemSubShading.apply {
                post {
                    layoutParams = layoutParams.apply {
                        width = (getAppDimension(R.dimen.dimen_200) * ratio).toInt()
                    }
                }
            }

            binding.tvSearchItemSubTitle.apply {
                post {
                    layoutParams = layoutParams.apply {
                        width = (getAppDimension(R.dimen.dimen_200) * ratio).toInt()
                    }
                }
            }
        }
    }

    interface IListener {
        fun onItemClick(itemId: String?)
    }
}

