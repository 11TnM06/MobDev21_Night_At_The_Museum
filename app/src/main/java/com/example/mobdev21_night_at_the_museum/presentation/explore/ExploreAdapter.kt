package com.example.mobdev21_night_at_the_museum.presentation.explore

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.example.mobdev21_night_at_the_museum.R
import com.example.mobdev21_night_at_the_museum.common.extension.loadImage
import com.example.mobdev21_night_at_the_museum.common.extension.loadImageBlur
import com.example.mobdev21_night_at_the_museum.common.extension.setOnSafeClick
import com.example.mobdev21_night_at_the_museum.common.recycleview.BaseVH
import com.example.mobdev21_night_at_the_museum.common.recycleview.MuseumAdapter
import com.example.mobdev21_night_at_the_museum.databinding.ExploreExhibitionItemBinding
import com.example.mobdev21_night_at_the_museum.databinding.ExploreItemItemBinding
import com.example.mobdev21_night_at_the_museum.domain.model.Exhibition
import com.example.mobdev21_night_at_the_museum.domain.model.Item
import com.example.mobdev21_night_at_the_museum.domain.model.MCollection

class ExploreAdapter : MuseumAdapter() {
    companion object {
        const val EXHIBITION_TYPE = 2002
        const val ITEM_TYPE = 2003

        const val LIKE_PAYLOAD = 3001
        const val COLLECTION_PAYLOAD = 3002
    }

    var listener: IListener? = null

    override fun getItemViewTypeCustom(position: Int): Int {
        return when (getDataAtPosition(position)) {
            is Exhibition -> EXHIBITION_TYPE
            is Item -> ITEM_TYPE
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getLayoutResource(viewType: Int): Int {
        return when (viewType) {
            EXHIBITION_TYPE -> R.layout.explore_exhibition_item
            ITEM_TYPE -> R.layout.explore_item_item
            else -> INVALID_RESOURCE
        }
    }

    override fun onCreateViewHolder(viewType: Int, binding: ViewDataBinding): BaseVH<*>? {
        return when (viewType) {
            EXHIBITION_TYPE -> ExhibitionVH(binding as ExploreExhibitionItemBinding)
            ITEM_TYPE -> ItemVH(binding as ExploreItemItemBinding)
            else -> null
        }
    }

    override fun getDiffUtil(oldList: List<Any>, newList: List<Any>): DiffUtil.Callback {
        return ExploreDiffUtil(oldList, newList)
    }

    inner class ExhibitionVH(private val binding: ExploreExhibitionItemBinding) : BaseVH<Exhibition>(binding) {
        init {
            binding.flExploreExhibitionBuyTicket.setOnSafeClick { getItem { listener?.onBuyTicket(it) } }
        }

        override fun onBind(data: Exhibition) {
            binding.ivExploreExhibitionThumb.loadImage(data.thumbnail)
            binding.ivExploreExhibitionMuseumThumb.loadImage(data.museumThumb)
            binding.tvExploreExhibitionName.text = data.name
            binding.tvExploreExhibitionMuseumInfo.text = data.getMuseumInfo()
            binding.tvExploreExhibitionDate.text = data.getExhibitionDate()
            binding.tvExploreExhibitionTitle.text = data.getExhibitionTitle()
            binding.ivExploreExhibitionBackground.loadImageBlur(data.thumbnail)
        }
    }

    inner class ItemVH(private val binding: ExploreItemItemBinding) : BaseVH<Item>(binding) {
        init {
            binding.ivExploreItemLike.setOnSafeClick { getItem { listener?.onLikeItem(it) } }
            binding.ivExploreItemShare.setOnSafeClick { getItem { listener?.onShareItem(it) } }
            binding.ivExploreItemZoom.setOnSafeClick { getItem { listener?.onZoomItem(it) } }
            binding.ivExploreItemThumbnail.setOnSafeClick { getItem { listener?.onZoomItem(it) } }
            binding.tvExploreItemCreator.setOnSafeClick { getItem { listener?.onViewItem(it.key) } }
            binding.tvExploreItemName.setOnSafeClick { getItem { listener?.onViewItem(it.key) } }
            binding.ivExploreItemCollectionIcon.setOnSafeClick { getItem { listener?.onViewCollection(it.collectionId) } }
            binding.tvExploreItemCollectionName.setOnSafeClick { getItem { listener?.onViewCollection(it.collectionId) } }
        }

        override fun onBind(data: Item) {
            getDataCollection(data)
            setLikeStatus(data)
            binding.tvExploreItemName.text = data.name
            binding.ivExploreItemBackground.loadImageBlur(data.thumbnail)
            binding.ivExploreItemThumbnail.loadImage(data.thumbnail)
            binding.tvExploreItemCreator.text = data.creatorName
        }

        override fun onBind(data: Item, payloads: List<Any>) {
            binding.apply {
                (payloads.firstOrNull() as? List<*>)?.forEach {
                    when (it) {
                        LIKE_PAYLOAD -> setLikeStatus(data)
                        COLLECTION_PAYLOAD -> updateCollection(data)
                    }
                }
            }
        }

        private fun updateCollection(data: Item) {
            binding.ivExploreItemCollectionIcon.loadImage(data.collection?.icon)
            binding.tvExploreItemCollectionName.text = data.collection?.name
        }

        private fun setLikeStatus(data: Item) {
            binding.ivExploreItemLike.setImageResource(
                when (data.safeIsLiked()) {
                    true -> R.drawable.ic_like_filled
                    else -> R.drawable.ic_like
                }
            )
        }

        private fun getDataCollection(data: Item) {
            if (data.collectionId != null) {
                val db = Firebase.firestore
                val collectionRef = db.collection("collections").document(data.collectionId!!)
                collectionRef.get().addOnSuccessListener { collectionSnapshot ->
                    data.collection = collectionSnapshot.toObject(MCollection::class.java)?.apply { key = collectionSnapshot.id }
                    updateCollection(data)
                }
            }
        }
    }

    interface IListener {
        fun onLikeItem(item: Item)
        fun onBuyTicket(exhibition: Exhibition)
        fun onZoomItem(item: Item)
        fun onShareItem(item: Item)
        fun onViewItem(itemId: String?)
        fun onViewCollection(collectionId: String?)
    }
}
