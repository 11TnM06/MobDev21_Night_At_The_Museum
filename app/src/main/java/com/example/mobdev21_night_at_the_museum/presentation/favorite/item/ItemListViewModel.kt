package com.example.mobdev21_night_at_the_museum.presentation.favorite.item

import android.graphics.drawable.Drawable
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.mobdev21_night_at_the_museum.common.BaseViewModel
import com.example.mobdev21_night_at_the_museum.common.extension.getApplication
import com.example.mobdev21_night_at_the_museum.domain.model.Item
import com.example.mobdev21_night_at_the_museum.presentation.widget.PswrdAdapter
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class ItemListViewModel : BaseViewModel() {
    companion object {
        const val VERTICAL_RATIO = 10 / 9f
    }

    var items: List<Item> = emptyList()
    var itemDisplays: MutableList<ItemDisplay> = mutableListOf()

    private var count = 0

    fun calculateSizeOfListImage(onSuccess: () -> Unit) {
        viewModelScope.launch {
            loadImageUrlListWithGlide(onSuccess)
        }
    }

    private fun loadImageUrlListWithGlide(onSuccess: () -> Unit) {
        count = items.size
        var hasLeft = false
        for (item in items) {
            Glide.with(getApplication()).load(item.thumbnail).listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        count--
                        if (count == 0) {
                            onSuccess.invoke()
                        }
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        count--
                        if (resource != null) {
                            val width = resource.intrinsicWidth
                            val height = resource.intrinsicHeight
                            itemDisplays.add(ItemDisplay().apply {
                                this.item = item
                                this.imageSize = ImageSize(height, width)
                                if (height / width.toFloat() > VERTICAL_RATIO) {
                                    if (hasLeft) {
                                        val item1 = itemDisplays.last()
                                        val item2 = this

                                        item1.countInRow = 2
                                        item2.countInRow = 2
                                        item1.isLeft = true
                                        item2.isLeft = false
                                        hasLeft = false

                                        val ratio1 = item1.imageSize.width.toFloat() / item1.imageSize.height
                                        val ratio2 = item2.imageSize.width.toFloat() / item2.imageSize.height

                                        // first / second
                                        var ratio = ratio1 / ratio2

                                        // second
                                        ratio = PswrdAdapter.SPAN_COUNT / (ratio + 1)
                                        val second = ratio.roundToInt()
                                        val first = PswrdAdapter.SPAN_COUNT - second

                                        itemDisplays.last().spanSize = first
                                        this.spanSize = second
                                    } else {
                                        hasLeft = true
                                    }
                                } else {
                                    hasLeft = false
                                }
                            })
                        }
                        if (count == 0) {
                            onSuccess.invoke()
                        }
                        return false
                    }
                }).submit()
        }
    }
}
