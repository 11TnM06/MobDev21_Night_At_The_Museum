package com.example.mobdev21_night_at_the_museum.presentation.favorite

import androidx.databinding.ViewDataBinding
import com.example.mobdev21_night_at_the_museum.R
import com.example.mobdev21_night_at_the_museum.common.extension.loadImage
import com.example.mobdev21_night_at_the_museum.common.recycleview.BaseVH
import com.example.mobdev21_night_at_the_museum.common.recycleview.MuseumAdapter
import com.example.mobdev21_night_at_the_museum.databinding.FavoriteStoryChildItemBinding
import com.example.mobdev21_night_at_the_museum.domain.model.Story

class FavoriteStoryAdapter : MuseumAdapter() {
    var listener: IListener? = null

    override fun getLayoutResource(viewType: Int) = R.layout.favorite_story_child_item

    override fun setupEmptyState() = Empty(overrideLayoutRes = R.layout.empty_favorite_stories_item)

    override fun onCreateViewHolder(viewType: Int, binding: ViewDataBinding): BaseVH<*>? {
        return FavoriteStoryVH(binding as FavoriteStoryChildItemBinding)
    }

    inner class FavoriteStoryVH(private val binding: FavoriteStoryChildItemBinding) : BaseVH<Story>(binding) {
        init {
            binding.root.setOnClickListener {
                getItem {
                    listener?.onStoryClick(it.key)
                }
            }
        }

        override fun onBind(data: Story) {
            binding.ivFavoriteStoryChildThumbnail.loadImage(data.thumbnail)
            binding.tvFavoriteStoryChildTitle.text = data.title
            binding.tvFavoriteStoryChildDescription.text = data.description
        }
    }

    interface IListener {
        fun onStoryClick(storyId: String?)
    }
}
