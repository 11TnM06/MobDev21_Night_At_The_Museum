package com.example.mobdev21_night_at_the_museum.presentation.item

import android.graphics.drawable.Drawable
import androidx.databinding.ViewDataBinding
import com.example.mobdev21_night_at_the_museum.R
import com.example.mobdev21_night_at_the_museum.common.extension.getAppDrawable
import com.example.mobdev21_night_at_the_museum.common.extension.getAppString
import com.example.mobdev21_night_at_the_museum.common.extension.setOnSafeClick
import com.example.mobdev21_night_at_the_museum.common.recycleview.BaseVH
import com.example.mobdev21_night_at_the_museum.common.recycleview.MuseumAdapter
import com.example.mobdev21_night_at_the_museum.databinding.ItemActionItemBinding

class ItemActionAdapter: MuseumAdapter() {
    var listener: IListener? = null

    override fun getLayoutResource(viewType: Int) = R.layout.item_action_item

    override fun onCreateViewHolder(viewType: Int, binding: ViewDataBinding): BaseVH<*>? {
        return ActionVH(binding as ItemActionItemBinding)
    }

    class ActionDisplay(val actionType: ACTION_TYPE) {
        fun getIcon(): Drawable? {
            return when (actionType) {
                ACTION_TYPE.ZOOM_IN -> getAppDrawable(R.drawable.ic_zoom_in)
                ACTION_TYPE.AR -> getAppDrawable(R.drawable.ic_ar)
                ACTION_TYPE.STREET -> getAppDrawable(R.drawable.ic_street_view)
            }
        }

        fun getDescription(): String {
            return when (actionType) {
                ACTION_TYPE.ZOOM_IN -> getAppString(R.string.zoom_in)
                ACTION_TYPE.AR -> getAppString(R.string.view_in_ar)
                ACTION_TYPE.STREET -> getAppString(R.string.street_view)
            }
        }
    }

    inner class ActionVH(private val binding: ItemActionItemBinding) : BaseVH<ActionDisplay>(binding) {
        init {
            binding.mcvItemAction.setOnSafeClick {
                getItem {
                    listener?.onActionClick(it.actionType)
                }
            }
        }

        override fun onBind(data: ActionDisplay) {
            binding.tvItemAction.text = data.getDescription()
            binding.ivItemActionIcon.setImageDrawable(data.getIcon())
        }
    }

    interface IListener {
        fun onActionClick(actionType: ACTION_TYPE)
    }
}

enum class ACTION_TYPE {
    ZOOM_IN, AR, STREET
}
