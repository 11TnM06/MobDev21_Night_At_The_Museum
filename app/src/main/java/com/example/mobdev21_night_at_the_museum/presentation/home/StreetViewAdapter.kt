package com.example.mobdev21_night_at_the_museum.presentation.home

import androidx.databinding.ViewDataBinding
import com.example.mobdev21_night_at_the_museum.R
import com.example.mobdev21_night_at_the_museum.common.extension.loadImage
import com.example.mobdev21_night_at_the_museum.common.extension.setOnSafeClick
import com.example.mobdev21_night_at_the_museum.common.recycleview.BaseVH
import com.example.mobdev21_night_at_the_museum.common.recycleview.MuseumAdapter
import com.example.mobdev21_night_at_the_museum.databinding.HomeStreetViewSubItemBinding
import com.example.mobdev21_night_at_the_museum.domain.model.StreetView

class StreetViewAdapter: MuseumAdapter() {
    var listener: IListener? = null

    override fun getLayoutResource(viewType: Int) = R.layout.home_street_view_sub_item

    override fun onCreateViewHolder(viewType: Int, binding: ViewDataBinding): BaseVH<*>? {
        return StreetViewVH(binding as HomeStreetViewSubItemBinding)
    }

    inner class StreetViewVH(private val binding: HomeStreetViewSubItemBinding) : BaseVH<StreetView>(binding) {
        init {
            binding.root.setOnSafeClick {
                getItem {
                    listener?.onStreetViewClick(it)
                }
            }
        }

        override fun onBind(data: StreetView) {
            binding.apply {
                ivHomeStreetViewThumbnail.loadImage(data.thumbnail)
                tvHomeStreetViewName.text = data.name
                tvHomeStreetViewPlace.text = data.place
            }
        }
    }

    interface IListener {
        fun onStreetViewClick(streetView: StreetView)
    }
}
