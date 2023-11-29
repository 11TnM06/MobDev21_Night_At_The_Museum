package com.example.mobdev21_night_at_the_museum.presentation.favorite

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.example.mobdev21_night_at_the_museum.R
import com.example.mobdev21_night_at_the_museum.common.extension.getAppColor
import com.example.mobdev21_night_at_the_museum.common.extension.getAppDrawable
import com.example.mobdev21_night_at_the_museum.common.extension.gone
import com.example.mobdev21_night_at_the_museum.common.extension.loadImage
import com.example.mobdev21_night_at_the_museum.common.extension.setOnSafeClick
import com.example.mobdev21_night_at_the_museum.common.extension.show
import com.example.mobdev21_night_at_the_museum.common.recycleview.BaseVH
import com.example.mobdev21_night_at_the_museum.common.recycleview.MuseumAdapter
import com.example.mobdev21_night_at_the_museum.databinding.FavoriteCollectionItemBinding
import com.example.mobdev21_night_at_the_museum.databinding.FavoriteGalleryEmptyItemBinding
import com.example.mobdev21_night_at_the_museum.databinding.FavoriteGalleryItemBinding
import com.example.mobdev21_night_at_the_museum.databinding.FavoriteHeaderItemBinding
import com.example.mobdev21_night_at_the_museum.databinding.FavoriteItemItemBinding
import com.example.mobdev21_night_at_the_museum.databinding.FavoriteStoryItemBinding
import com.example.mobdev21_night_at_the_museum.domain.model.Gallery
import com.example.mobdev21_night_at_the_museum.domain.model.Item
import com.example.mobdev21_night_at_the_museum.domain.model.MCollection
import com.example.mobdev21_night_at_the_museum.domain.model.Story
import com.example.mobdev21_night_at_the_museum.presentation.widget.COLLECTION_MODE

class FavoriteAdapter : MuseumAdapter() {
    companion object {
        const val HEADER_VIEW_TYPE = 1409
        const val ITEM_VIEW_TYPE = 1410
        const val STORY_VIEW_TYPE = 1411
        const val COLLECTION_VIEW_TYPE = 1412
        const val GALLERY_VIEW_TYPE = 1413
        const val GALLERY_EMPTY_VIEW_TYPE = 1414

        const val HEADER_TAB_PAYLOAD = "HEADER_TAB_PAYLOAD"
        const val AVATAR_PAYLOAD = "AVATAR_PAYLOAD"
    }

    var listener: IListener? = null

    override fun getLayoutResource(viewType: Int): Int {
        return when (viewType) {
            HEADER_VIEW_TYPE -> R.layout.favorite_header_item
            ITEM_VIEW_TYPE -> R.layout.favorite_item_item
            STORY_VIEW_TYPE -> R.layout.favorite_story_item
            COLLECTION_VIEW_TYPE -> R.layout.favorite_collection_item
            GALLERY_VIEW_TYPE -> R.layout.favorite_gallery_item
            GALLERY_EMPTY_VIEW_TYPE -> R.layout.favorite_gallery_empty_item
            else -> INVALID_RESOURCE
        }
    }

    override fun getItemViewTypeCustom(position: Int): Int {
        return when (getDataAtPosition(position)) {
            is HeaderDisplay -> HEADER_VIEW_TYPE
            is ItemDisplay -> ITEM_VIEW_TYPE
            is StoryDisplay -> STORY_VIEW_TYPE
            is CollectionDisplay -> COLLECTION_VIEW_TYPE
            is Gallery -> GALLERY_VIEW_TYPE
            is GalleryEmpty -> GALLERY_EMPTY_VIEW_TYPE
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getDiffUtil(oldList: List<Any>, newList: List<Any>): DiffUtil.Callback {
        return FavoriteDiffUtil(oldList, newList)
    }

    override fun onCreateViewHolder(viewType: Int, binding: ViewDataBinding): BaseVH<*>? {
        return when (viewType) {
            HEADER_VIEW_TYPE -> HeaderVH(binding as FavoriteHeaderItemBinding)
            ITEM_VIEW_TYPE -> ItemVH(binding as FavoriteItemItemBinding)
            STORY_VIEW_TYPE -> StoryVH(binding as FavoriteStoryItemBinding)
            COLLECTION_VIEW_TYPE -> CollectionVH(binding as FavoriteCollectionItemBinding)
            GALLERY_VIEW_TYPE -> GalleryVH(binding as FavoriteGalleryItemBinding)
            GALLERY_EMPTY_VIEW_TYPE -> GalleryEmptyVH(binding as FavoriteGalleryEmptyItemBinding)
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    class HeaderDisplay {
        var avatarUrl: String? = null
        var isFavoriteTab: Boolean = true
    }

    class ItemDisplay {
        var count: Int? = null
        var itemList: List<Item>? = null
    }

    class StoryDisplay {
        var count: Int? = null
        var storyList: List<Story>? = null
    }

    class CollectionDisplay {
        var count: Int? = null
        var collectionList: List<MCollection>? = null
    }

    class GalleryEmpty {

    }

    inner class HeaderVH(private val binding: FavoriteHeaderItemBinding) : BaseVH<HeaderDisplay>(binding) {
        init {
            binding.flFavoriteHeaderTabFavorite.setOnSafeClick {
                listener?.onFavoriteTab()
            }

        }

        override fun onBind(data: HeaderDisplay) {
            setAvatar(data)
            setTab(data)
        }

        override fun onBind(data: HeaderDisplay, payloads: List<Any>) {
            binding.apply {
                (payloads.firstOrNull() as? List<*>)?.forEach {
                    when (it) {
                        AVATAR_PAYLOAD -> setAvatar(data)
                        HEADER_TAB_PAYLOAD -> setTab(data)
                    }
                }
            }
        }

        private fun setAvatar(data: HeaderDisplay) {
            binding.ivProfileAvatar.loadImage(data.avatarUrl, placeHolder = getAppDrawable(R.drawable.ic_no_picture))
        }

        private fun setTab(data: HeaderDisplay) {
            binding.vFavoriteHeaderTabFavoriteEnable.show()
            binding.vFavoriteHeaderTabFavoriteDisable.gone()
            binding.tvFavoriteTabFavorite.setTextColor(getAppColor(R.color.main_black))
        }

    }

    inner class ItemVH(private val binding: FavoriteItemItemBinding) : BaseVH<ItemDisplay>(binding) {
        init {
            binding.tvFavoriteItemViewAll.setOnSafeClick {
                listener?.onViewAllItem()
            }
            binding.ivFavoriteItemItem1.setOnSafeClick {
                getItem {
                    listener?.onItemClick(it.itemList?.getOrNull(0)?.key)
                }
            }
            binding.ivFavoriteItemItem2.setOnSafeClick {
                getItem {
                    listener?.onItemClick(it.itemList?.getOrNull(1)?.key)
                }
            }
            binding.ivFavoriteItemItem3.setOnSafeClick {
                getItem {
                    listener?.onItemClick(it.itemList?.getOrNull(2)?.key)
                }
            }
            binding.ivFavoriteItemItem4.setOnSafeClick {
                getItem {
                    listener?.onItemClick(it.itemList?.getOrNull(3)?.key)
                }
            }
        }

        override fun onBind(data: ItemDisplay) {
            binding.ivFavoriteItemItem1.loadImage(data.itemList?.getOrNull(0)?.thumbnail, placeHolder = getAppDrawable(R.color.gray_super_light))
            binding.ivFavoriteItemItem2.loadImage(data.itemList?.getOrNull(1)?.thumbnail, placeHolder = getAppDrawable(R.color.gray_super_light))
            binding.ivFavoriteItemItem3.loadImage(data.itemList?.getOrNull(2)?.thumbnail, placeHolder = getAppDrawable(R.color.gray_super_light))
            binding.ivFavoriteItemItem4.loadImage(data.itemList?.getOrNull(3)?.thumbnail, placeHolder = getAppDrawable(R.color.gray_super_light))
            binding.tvFavoriteItemCount.text = data.count.toString()
        }
    }

    inner class StoryVH(private val binding: FavoriteStoryItemBinding) : BaseVH<StoryDisplay>(binding) {
        private val adapter: FavoriteStoryAdapter by lazy { FavoriteStoryAdapter() }

        init {
            adapter.listener = object : FavoriteStoryAdapter.IListener {
                override fun onStoryClick(storyId: String?) {
                    listener?.onStoryClick(storyId)
                }
            }
            binding.cvFavoriteStory.apply {
                setAdapter(this@StoryVH.adapter)
                setLayoutManager(COLLECTION_MODE.HORIZONTAL)
            }
            binding.tvFavoriteStoryViewAll.setOnSafeClick {
                listener?.onViewAllStory()
            }
        }

        override fun onBind(data: StoryDisplay) {
            binding.cvFavoriteStory.submitList(data.storyList)
            binding.tvFavoriteStoryCount.text = data.count.toString()
        }
    }

    inner class CollectionVH(private val binding: FavoriteCollectionItemBinding) : BaseVH<CollectionDisplay>(binding) {
        private val adapter: FavoriteCollectionAdapter by lazy { FavoriteCollectionAdapter() }

        init {
            adapter.listener = object : FavoriteCollectionAdapter.IListener {
                override fun onCollectionClick(collectionId: String?) {
                    listener?.onCollectionClick(collectionId)
                }
            }
            binding.cvFavoriteCollection.apply {
                setAdapter(this@CollectionVH.adapter)
                setLayoutManager(COLLECTION_MODE.HORIZONTAL)
            }
            binding.tvFavoriteCollectionViewAll.setOnSafeClick {
                listener?.onViewAllCollection()
            }
        }

        override fun onBind(data: CollectionDisplay) {
            binding.cvFavoriteCollection.submitList(data.collectionList)
            binding.tvFavoriteCollectionCount.text = data.count.toString()
        }
    }

    inner class GalleryVH(private val binding: FavoriteGalleryItemBinding) : BaseVH<Gallery>(binding) {
        init {
            binding.tvFavoriteGalleryMore.setOnSafeClick {
                getItem {
                    listener?.onMoreGallery(it)
                }
            }
        }

        override fun onBind(data: Gallery) {
            binding.tvFavoriteGalleryTitle.text = data.title
            if (data.description.isNullOrEmpty()) {
                binding.tvFavoriteGalleryDescription.gone()
            } else {
                binding.tvFavoriteGalleryDescription.show()
                binding.tvFavoriteGalleryDescription.text = data.description
            }
            when (data.items?.size) {
                1 -> {
                    binding.mcvFavoriteGalleryCountOne.show()
                    binding.llFavoriteGalleryCountTwo.gone()
                    binding.llFavoriteGalleryCountThree.gone()

                    binding.ivFavoriteGalleryCountOne.loadImage(data.items?.getOrNull(0)?.thumbnail)
                }
                2 -> {
                    binding.mcvFavoriteGalleryCountOne.gone()
                    binding.llFavoriteGalleryCountTwo.show()
                    binding.llFavoriteGalleryCountThree.gone()

                    binding.ivFavoriteGalleryCountTwo1.loadImage(data.items?.getOrNull(0)?.thumbnail)
                    binding.ivFavoriteGalleryCountTwo2.loadImage(data.items?.getOrNull(1)?.thumbnail)
                }
                else -> {
                    binding.mcvFavoriteGalleryCountOne.gone()
                    binding.llFavoriteGalleryCountTwo.gone()
                    binding.llFavoriteGalleryCountThree.show()

                    binding.ivFavoriteGalleryCountThree1.loadImage(data.items?.getOrNull(0)?.thumbnail)
                    binding.ivFavoriteGalleryCountThree2.loadImage(data.items?.getOrNull(1)?.thumbnail)
                    binding.ivFavoriteGalleryCountThree3.loadImage(data.items?.getOrNull(2)?.thumbnail)
                }
            }
        }
    }

    inner class GalleryEmptyVH(private val binding: FavoriteGalleryEmptyItemBinding) : BaseVH<Gallery>(binding) {

    }


    interface IListener {
        fun onFavoriteTab()
        fun onGalleriesTab()
        fun onViewAllItem()
        fun onViewAllStory()
        fun onViewAllCollection()
        fun onMoreGallery(gallery: Gallery)
        fun onItemClick(itemId: String?)
        fun onStoryClick(storyId: String?)
        fun onCollectionClick(collectionId: String?)
    }
}
