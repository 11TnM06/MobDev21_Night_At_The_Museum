package com.example.mobdev21_night_at_the_museum.presentation.collection.all.az

import androidx.databinding.ViewDataBinding
import com.example.mobdev21_night_at_the_museum.R
import com.example.mobdev21_night_at_the_museum.common.extension.getAppDrawable
import com.example.mobdev21_night_at_the_museum.common.extension.setOnSafeClick
import com.example.mobdev21_night_at_the_museum.common.recycleview.BaseVH
import com.example.mobdev21_night_at_the_museum.common.recycleview.MuseumAdapter
import com.example.mobdev21_night_at_the_museum.databinding.AzCollectionItemBinding

class AZCollectionAdapter: MuseumAdapter() {
    companion object {
        const val SELECT_PAYLOAD = "SELECT_PAYLOAD"
    }

    var listener: IListener? = null

    override fun getLayoutResource(viewType: Int) = R.layout.az_collection_item

    override fun onCreateViewHolder(viewType: Int, binding: ViewDataBinding): BaseVH<*>? {
        return LetterVH(binding as AzCollectionItemBinding)
    }

    private fun selectNewItem(newItem: LetterDisplay) {
        val oldSelectItemIndex = dataList.indexOfFirst {
            (it as? LetterDisplay)?.isSelected == true && it.letter != newItem.letter
        }
        val oldSelectItem = dataList.getOrNull(oldSelectItemIndex) as? LetterDisplay
        if (oldSelectItem != null) {
            oldSelectItem.isSelected = false
            notifyItemChanged(oldSelectItemIndex, listOf(SELECT_PAYLOAD))
        }
    }

    inner class LetterVH(val binding: AzCollectionItemBinding): BaseVH<LetterDisplay>(binding) {
        init {
            binding.flAzCollection.setOnSafeClick {
                getItem { currentItem ->
                    currentItem.isSelected = true
                    updateSelectState(currentItem)
                    selectNewItem(currentItem)
                    listener?.onLetterClick(currentItem)
                }
            }
        }

        override fun onBind(data: LetterDisplay) {
            binding.tvAzCollection.text = data.letter.toString()
            updateSelectState(data)
        }

        override fun onBind(data: LetterDisplay, payloads: List<Any>) {
            binding.apply {
                (payloads.firstOrNull() as? List<*>)?.forEach {
                    when (it) {
                        SELECT_PAYLOAD -> updateSelectState(data)
                    }
                }
            }
        }

        private fun updateSelectState(data: LetterDisplay) {
            if (data.isSelected) {
                binding.flAzCollection.background = getAppDrawable(R.drawable.shape_circle_gray_super_light)
            } else {
                binding.flAzCollection.background = null
            }
        }
    }

    class LetterDisplay(val letter: Char) {
        var isSelected: Boolean = false
    }

    interface IListener {
        fun onLetterClick(letterDisplay: LetterDisplay)
    }
}
