package com.example.mobdev21_night_at_the_museum.presentation.item

import android.annotation.SuppressLint
import android.util.TypedValue
import android.view.ViewGroup.MarginLayoutParams
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.example.mobdev21_night_at_the_museum.R
import com.example.mobdev21_night_at_the_museum.common.FontSpan
import com.example.mobdev21_night_at_the_museum.common.SpannableBuilder
import com.example.mobdev21_night_at_the_museum.common.extension.*
import com.example.mobdev21_night_at_the_museum.common.recycleview.BaseVH
import com.example.mobdev21_night_at_the_museum.common.recycleview.MuseumAdapter
import com.example.mobdev21_night_at_the_museum.databinding.*
import com.example.mobdev21_night_at_the_museum.domain.model.Item
import com.example.mobdev21_night_at_the_museum.domain.model.ItemDetail
import com.example.mobdev21_night_at_the_museum.presentation.widget.COLLECTION_MODE

class ItemAdapter : MuseumAdapter() {
    companion object {
        const val TRANSPARENT_TYPE = 1408
        const val CHOOSE_ACTION_TYPE = 1409
        const val TITLE_TYPE = 1410
        const val DESCRIPTION_TYPE = 1411
        const val DETAIL_TITLE_TYPE = 1414
        const val DETAIL_INFO_TYPE = 1412
        const val RECOMMEND_TYPE = 1413

        const val LIKE_PAYLOAD = "LIKE_PAYLOAD"
        const val DETAIL_INFO_PAYLOAD = "DETAIL_INFO_PAYLOAD"
    }

    var listener: IListener? = null

    override fun getDiffUtil(oldList: List<Any>, newList: List<Any>): DiffUtil.Callback {
        return ItemDiffUtil(oldList, newList)
    }

    override fun getItemViewTypeCustom(position: Int): Int {
        return when (getDataAtPosition(position)) {
            is TransparentDisplay -> TRANSPARENT_TYPE
            is ChooseActionDisplay -> CHOOSE_ACTION_TYPE
            is TitleDisplay -> TITLE_TYPE
            is DescriptionDisplay -> DESCRIPTION_TYPE
            is DetailTitleDisplay -> DETAIL_TITLE_TYPE
            is DetailInfoDisplay -> DETAIL_INFO_TYPE
            is RecommendDisplay -> RECOMMEND_TYPE
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getLayoutResource(viewType: Int): Int {
        return when (viewType) {
            TRANSPARENT_TYPE -> R.layout.item_transparent_item
            CHOOSE_ACTION_TYPE -> R.layout.item_choose_action_item
            TITLE_TYPE -> R.layout.item_title_item
            DESCRIPTION_TYPE -> R.layout.item_description_item
            DETAIL_TITLE_TYPE -> R.layout.item_detail_title_item
            DETAIL_INFO_TYPE -> R.layout.item_detail_info_item
            RECOMMEND_TYPE -> R.layout.item_recommend_item
            else -> throw IllegalArgumentException("getLayoutResource: viewType is invalid")
        }
    }

    override fun onCreateViewHolder(viewType: Int, binding: ViewDataBinding): BaseVH<*>? {
        return when (viewType) {
            TRANSPARENT_TYPE -> TransparentVH(binding as ItemTransparentItemBinding)
            CHOOSE_ACTION_TYPE -> ChooseActionVH(binding as ItemChooseActionItemBinding)
            TITLE_TYPE -> TitleVH(binding as ItemTitleItemBinding)
            DESCRIPTION_TYPE -> DescriptionVH(binding as ItemDescriptionItemBinding)
            DETAIL_TITLE_TYPE -> DetailTitleVH(binding as ItemDetailTitleItemBinding)
            DETAIL_INFO_TYPE -> DetailInfoVH(binding as ItemDetailInfoItemBinding)
            RECOMMEND_TYPE -> RecommendVH(binding as ItemRecommendItemBinding)
            else -> throw IllegalArgumentException("onCreateViewHolder: viewType is invalid")
        }
    }

    class TransparentDisplay {

    }

    class ChooseActionDisplay {
        var actions: List<ItemActionAdapter.ActionDisplay>? = null
    }

    data class TitleDisplay(
        var title: String? = null,
        var creator: String? = null,
        var time: String? = null,
        var collectionName: String? = null,
        var collectionThumb: String? = null,
        var isLike: Boolean = false
    )

    class DescriptionDisplay {
        var description: String? = null
    }

    class DetailTitleDisplay {
        var isOpen = false
    }

    class DetailInfoDisplay {
        var itemDetail: ItemDetail? = null
        var isLast = false
    }

    class RecommendDisplay {
        var currentItemId: String? = null
        var list: List<Item>? = null
    }

    inner class TransparentVH(private val binding: ItemTransparentItemBinding) : BaseVH<TransparentDisplay>(binding) {
        init {
            updateHeight()
        }

        private fun updateHeight() {
            val tv = TypedValue()
            if (getApplication().theme.resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
                val actionBarSize = TypedValue.complexToDimensionPixelSize(tv.data, getApplication().resources.displayMetrics)
                val screenHeight = getApplication().resources.displayMetrics.heightPixels - getApplication().getStatusBarHeight()
                binding.vItemTransparent.layoutParams.apply {
                    height = screenHeight - getAppDimensionPixel(R.dimen.dimen_230) - actionBarSize
                }
            }
        }
    }

    inner class ChooseActionVH(private val binding: ItemChooseActionItemBinding) : BaseVH<ChooseActionDisplay>(binding) {
        private val actionAdapter by lazy { ItemActionAdapter() }

        init {
            actionAdapter.listener = object : ItemActionAdapter.IListener {
                override fun onActionClick(actionType: ACTION_TYPE) {
                    listener?.onActionClick(actionType)
                }
            }
            binding.cvItem.apply {
                setAdapter(actionAdapter)
                setLayoutManager(COLLECTION_MODE.HORIZONTAL)
            }
        }

        override fun onBind(data: ChooseActionDisplay) {
            binding.cvItem.submitList(data.actions)
        }
    }

    inner class TitleVH(private val binding: ItemTitleItemBinding) : BaseVH<TitleDisplay>(binding) {
        init {
            binding.ivItemLike.setOnSafeClick {
                getItem {
                    if (it.isLike) {
                        listener?.onDislikeClick()
                    } else {
                        listener?.onLikeClick()
                    }
                }
            }
            binding.ivItemShare.setOnSafeClick {
                listener?.onShareClick()
            }
            binding.ivItemCollectionThumb.setOnSafeClick { getItem { listener?.onCollectionClick() } }
            binding.tvItemCollection.setOnSafeClick { getItem { listener?.onCollectionClick() } }
        }

        @SuppressLint("SetTextI18n")
        override fun onBind(data: TitleDisplay) {
            binding.apply {
                tvItemName.text = data.title
                tvItemCreator.text = data.creator + "  " + data.time
                tvItemCollection.text = data.collectionName
                ivItemCollectionThumb.loadImage(data.collectionThumb)
            }
            updateLikeStatus(data)
        }

        override fun onBind(data: TitleDisplay, payloads: List<Any>) {
            binding.apply {
                (payloads.firstOrNull() as? List<*>)?.forEach {
                    when (it) {
                        LIKE_PAYLOAD -> updateLikeStatus(data)
                    }
                }
            }
        }

        private fun updateLikeStatus(data: TitleDisplay) {
            if (data.isLike) {
                binding.ivItemLike.setImageResource(R.drawable.ic_like_filled_black)
            } else {
                binding.ivItemLike.setImageResource(R.drawable.ic_like_black)
            }
        }
    }

    inner class DescriptionVH(private val binding: ItemDescriptionItemBinding) : BaseVH<DescriptionDisplay>(binding) {
        init {
            binding.tvItemDescriptionReadMore.setOnSafeClick {
                binding.tvItemDescription.apply {
                    if (maxLines == 3) {
                        maxLines = Integer.MAX_VALUE
                        binding.tvItemDescriptionReadMore.text = getApplication().getString(R.string.read_less)
                    } else {
                        maxLines = 3
                        binding.tvItemDescriptionReadMore.text = getApplication().getString(R.string.read_more)
                    }
                }
            }
        }

        override fun onBind(data: DescriptionDisplay) {
            binding.tvItemDescription.text = data.description
        }
    }

    inner class DetailTitleVH(private val binding: ItemDetailTitleItemBinding) : BaseVH<DetailTitleDisplay>(binding) {
        init {
            binding.flItemDetailTitleContainer.setOnSafeClick {
                getItem {
                    if (it.isOpen) {
                        binding.ivItemDetailTitleArrow.animate().rotationBy(180f).start()
                    } else {
                        binding.ivItemDetailTitleArrow.animate().rotationBy(-180f).start()
                    }
                    listener?.onDetailTitleClick(it.isOpen)
                }
            }
        }
    }

    inner class DetailInfoVH(private val binding: ItemDetailInfoItemBinding) : BaseVH<DetailInfoDisplay>(binding) {
        @SuppressLint("SetTextI18n")
        override fun onBind(data: DetailInfoDisplay) {
            binding.tvItemDetailInfo.text = SpannableBuilder().appendText(data.itemDetail?.title)
                .withSpan(FontSpan(getAppFont(R.font.roboto_bold)))
                .appendText(" " + data.itemDetail?.description)
                .withSpan(FontSpan(getAppFont(R.font.roboto_regular)))
                .spannedText

            if (data.isLast) {
                (binding.tvItemDetailInfo.layoutParams as MarginLayoutParams).bottomMargin = getAppDimensionPixel(R.dimen.dimen_28)
            } else {
                (binding.tvItemDetailInfo.layoutParams as MarginLayoutParams).bottomMargin = getAppDimensionPixel(R.dimen.dimen_2)
            }
        }
    }

    inner class RecommendVH(private val binding: ItemRecommendItemBinding) : BaseVH<RecommendDisplay>(binding) {
        private val adapter by lazy { ItemRecommendAdapter() }

        init {
            adapter.listener = object : ItemRecommendAdapter.IListener {
                override fun onClickItem(itemId: String?) {
                    listener?.onRecommendedItemClick(itemId)
                }
            }

            binding.cvItemRecommendItem.apply {
                setAdapter(this@RecommendVH.adapter)
                setLayoutManager(COLLECTION_MODE.HORIZONTAL)
            }
        }

        override fun onBind(data: RecommendDisplay) {
            if (data.list == null) {
                getDataFromDb(data)
            } else {
                binding.cvItemRecommendItem.submitList(data.list)
            }
        }

        private fun getDataFromDb(data: RecommendDisplay) {
            if (data.currentItemId != null) {
                val db = Firebase.firestore
                val itemListRef = db.collection("items")
                    .whereNotEqualTo(FieldPath.documentId(), data.currentItemId!!)
                    .limit(10)
                itemListRef.get().addOnSuccessListener { itemListSnapshot ->
                    data.list = itemListSnapshot.documents.mapNotNull { itemSnapshot ->
                        itemSnapshot.toObject(Item::class.java)?.apply { key = itemSnapshot.id }
                    }
                    data.list?.shuffled()
                    binding.cvItemRecommendItem.submitList(data.list)
                }
            }
        }
    }

    interface IListener {
        fun onActionClick(actionType: ACTION_TYPE)
        fun onDetailTitleClick(isOpen: Boolean)
        fun onLikeClick()
        fun onDislikeClick()
        fun onShareClick()
        fun onCollectionClick()
        fun onRecommendedItemClick(itemId: String?)
    }
}
