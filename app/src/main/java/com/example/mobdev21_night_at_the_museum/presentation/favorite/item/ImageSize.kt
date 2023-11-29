package com.example.mobdev21_night_at_the_museum.presentation.favorite.item

import com.example.mobdev21_night_at_the_museum.domain.model.Item

data class ImageSize(
    val height: Int,
    val width: Int
)

class ItemDisplay {
    var item: Item? = null
    var imageSize: ImageSize = ImageSize(1, 1)
    var countInRow: Int = 1
    var spanSize: Int = 1
    var isLeft: Boolean? = null
}
