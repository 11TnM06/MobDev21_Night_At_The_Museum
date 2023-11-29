package com.example.mobdev21_night_at_the_museum.presentation.download

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.example.mobdev21_night_at_the_museum.R
import com.example.mobdev21_night_at_the_museum.common.extension.loadImage
import com.example.mobdev21_night_at_the_museum.common.extension.setOnSafeClick
import com.example.mobdev21_night_at_the_museum.common.recycleview.BaseVH
import com.example.mobdev21_night_at_the_museum.common.recycleview.MuseumAdapter
import com.example.mobdev21_night_at_the_museum.databinding.DownloadItemBinding

class DownloadAdapter : MuseumAdapter() {
    var listener: IListener? = null

    override fun setupEmptyState(): Empty {
        return Empty(overrideLayoutRes = R.layout.download_empty_item)
    }

    override fun getDiffUtil(oldList: List<Any>, newList: List<Any>): DiffUtil.Callback {
        return DownloadDiffUtil(oldList, newList)
    }

    override fun getLayoutResource(viewType: Int): Int {
        return R.layout.download_item
    }

    override fun onCreateViewHolder(viewType: Int, binding: ViewDataBinding): BaseVH<*>? {
        return DownloadVH(binding as DownloadItemBinding)
    }

    inner class DownloadVH(val binding: DownloadItemBinding) : BaseVH<DownloadItem>(binding) {
        init {
            binding.ivDownloadDelete.setOnSafeClick { getItem { listener?.onDelete(it) } }
            binding.flDownloadRoot.setOnSafeClick { getItem { listener?.onViewItem(it.id) } }
        }

        override fun onBind(data: DownloadItem) {
            binding.tvDownloadName.text = data.name
            binding.tvDownloadSize.text = data.size
            binding.ivDownloadThumbnail.loadImage(data.thumbnail)
        }
    }

    data class DownloadItem(
        var id: String? = null,
        var name: String? = null,
        var thumbnail: String? = null,
        var size: String? = null
    )

    interface IListener {
        fun onDelete(item: DownloadItem)
        fun onViewItem(id: String?)
    }
}
