package com.example.mobdev21_night_at_the_museum.presentation.story

import android.view.ViewGroup.MarginLayoutParams
import androidx.databinding.ViewDataBinding
import com.example.mobdev21_night_at_the_museum.R
import com.example.mobdev21_night_at_the_museum.common.recycleview.BaseVH
import com.example.mobdev21_night_at_the_museum.common.recycleview.MuseumAdapter
import com.example.mobdev21_night_at_the_museum.databinding.StoryLineItemBinding

class StoryLineAdapter:MuseumAdapter() {
    override fun getLayoutResource(viewType: Int): Int {
        return R.layout.story_line_item
    }

    override fun onCreateViewHolder(viewType: Int, binding: ViewDataBinding): BaseVH<*>? {
        return StoryLineVH(binding as StoryLineItemBinding)
    }

    inner class StoryLineVH(private val binding: StoryLineItemBinding): BaseVH<StoryLineDisplay>(binding) {
        override fun onBind(data: StoryLineDisplay) {
            binding.vStoryLine.apply{
                layoutParams = (layoutParams as MarginLayoutParams).apply {
                    width = (data.width * 9 / 10f).toInt()
                    marginStart = (data.width / 20f).toInt()
                    marginEnd = (data.width / 20f).toInt()
                }
            }
            if (data.isWhite) {
                binding.vStoryLine.setBackgroundResource(R.color.white)
            } else {
                binding.vStoryLine.setBackgroundResource(R.color.gray)
            }
        }
    }

    data class StoryLineDisplay (
        var width: Float,
        var isWhite: Boolean = false
    )
}
