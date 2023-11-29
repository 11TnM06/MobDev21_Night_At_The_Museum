package com.example.mobdev21_night_at_the_museum.common

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.example.mobdev21_night_at_the_museum.common.binding.CORNER_TYPE

interface IImageLoader {
    fun loadImage(
        view: ImageView,
        url: String?,
        placeHolder: Drawable? = null,
        ignoreCache: Boolean = false
    )

    fun loadImageBlur(
        view: ImageView,
        url: String?,
        placeHolder: Drawable? = null,
        ignoreCache: Boolean = false,
        radius: Int,
        sampling: Int
    )

    fun loadImage(
        view: ImageView,
        drawable: Drawable?,
        placeHolder: Drawable? = null,
        ignoreCache: Boolean = false
    )

    fun loadImageBase64(
        view: ImageView,
        base64: String?,
        placeHolder: Drawable? = null,
        ignoreCache: Boolean = false
    )

    fun loadRoundCornerImage(
        view: ImageView,
        url: String?,
        corner: Int,
        placeHolder: Drawable? = null,
        ignoreCache: Boolean = false,
        cornerType: CORNER_TYPE = CORNER_TYPE.ALL,
    )

    fun loadCircleImage(
        view: ImageView,
        url: String?,
        placeHolder: Drawable? = null,
        ignoreCache: Boolean = false,
    )

    fun loadGif(
        view: ImageView,
        gif: Int,
        placeHolder: Drawable? = null,
        ignoreCache: Boolean = false,
    )
}
