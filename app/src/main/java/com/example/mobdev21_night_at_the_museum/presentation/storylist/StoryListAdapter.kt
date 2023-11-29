package com.example.mobdev21_night_at_the_museum.presentation.storylist

import androidx.databinding.ViewDataBinding
import com.example.mobdev21_night_at_the_museum.R
import com.example.mobdev21_night_at_the_museum.common.extension.getAppString
import com.example.mobdev21_night_at_the_museum.common.extension.loadImage
import com.example.mobdev21_night_at_the_museum.common.recycleview.BaseVH
import com.example.mobdev21_night_at_the_museum.common.recycleview.GridMuseumAdapter
import com.example.mobdev21_night_at_the_museum.databinding.ItemListTitleItemBinding
import com.example.mobdev21_night_at_the_museum.databinding.StoryListItemBinding
import com.example.mobdev21_night_at_the_museum.domain.model.Story

class StoryListAdapter : GridMuseumAdapter() {
    companion object {
        const val TITLE_TYPE = 1410
        const val STORY_TYPE = 1411
    }

    var listener: IListener? = null

    override fun getItemCountInRow(viewType: Int): Int {
        return when (viewType) {
            TITLE_TYPE -> 1
            STORY_TYPE -> 2
            else -> throw IllegalArgumentException("getItemCountInRow: viewType is invalid")
        }
    }

    override fun getItemViewTypeCustom(position: Int): Int {
        return if (position == 0) {
            TITLE_TYPE
        } else {
            STORY_TYPE
        }
    }

    override fun getLayoutResource(viewType: Int): Int {
        return when (viewType) {
            TITLE_TYPE -> R.layout.item_list_title_item
            STORY_TYPE -> R.layout.story_list_item
            else -> throw IllegalArgumentException("getLayoutResource: viewType is invalid")
        }
    }

    override fun setupEmptyState() = Empty(overrideLayoutRes = R.layout.empty_favorite_stories_item)

    override fun onCreateViewHolder(viewType: Int, binding: ViewDataBinding): BaseVH<*>? {
        return when (viewType) {
            TITLE_TYPE -> TitleVH(binding as ItemListTitleItemBinding)
            STORY_TYPE -> FavoriteStoryVH(binding as StoryListItemBinding)
            else -> throw IllegalArgumentException("onCreateViewHolder: viewType is invalid")
        }
    }

    inner class TitleVH(private val binding: ItemListTitleItemBinding) : BaseVH<Int>(binding) {
        override fun onBind(data: Int) {
            binding.tvItemListTitle.text = getTitleText(data)
        }

        private fun getTitleText(data: Int): String {
            return getAppString(R.string.stories) + " - " + data
        }
    }

    inner class FavoriteStoryVH(private val binding: StoryListItemBinding) : BaseVH<Story>(binding) {
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
