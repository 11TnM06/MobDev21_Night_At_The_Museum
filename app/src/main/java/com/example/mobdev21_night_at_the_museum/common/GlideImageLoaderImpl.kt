package com.example.mobdev21_night_at_the_museum.common

import android.graphics.drawable.Drawable
import android.util.Base64
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.mobdev21_night_at_the_museum.common.binding.CORNER_TYPE
import jp.wasabeef.glide.transformations.BlurTransformation
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

class GlideImageLoaderImpl : IImageLoader {
    override fun loadImage(
        view: ImageView,
        url: String?,
        placeHolder: Drawable?,
        ignoreCache: Boolean
    ) {
        try {
            Glide.with(view)
                .load(url)
                .placeholder(placeHolder)
                .skipMemoryCache(ignoreCache)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(view)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun loadImage(
        view: ImageView,
        drawable: Drawable?,
        placeHolder: Drawable?,
        ignoreCache: Boolean
    ) {
        try {
            Glide.with(view)
                .load(drawable)
                .placeholder(placeHolder)
                .skipMemoryCache(ignoreCache)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(view)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun loadImageBase64(
        view: ImageView,
        base64: String?,
        placeHolder: Drawable?,
        ignoreCache: Boolean
    ) {
        try {
            val imageByteArray: ByteArray = Base64.decode(base64, Base64.DEFAULT)
            Glide.with(view)
                .load(imageByteArray)
                .placeholder(placeHolder)
                .skipMemoryCache(ignoreCache)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(view)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun loadRoundCornerImage(
        view: ImageView,
        url: String?,
        corner: Int,
        placeHolder: Drawable?,
        ignoreCache: Boolean,
        cornerType: CORNER_TYPE
    ) {
        try {

            val type = RoundedCornersTransformation.CornerType.valueOf(cornerType.toString())
            val rct = RoundedCornersTransformation(corner, 0, type)
            val requestOptions = RequestOptions.bitmapTransform(rct)

            Glide.with(view)
                .load(url)
                .apply(requestOptions)
                .placeholder(placeHolder)
                .skipMemoryCache(ignoreCache)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(view)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun loadCircleImage(
        view: ImageView,
        url: String?,
        placeHolder: Drawable?,
        ignoreCache: Boolean
    ) {
        try {
            val rct = RoundedCornersTransformation(view.width / 2, 0)
            val requestOptions = RequestOptions.bitmapTransform(rct)

            Glide.with(view)
                .load(url)
                .apply(requestOptions)
                .placeholder(placeHolder)
                .skipMemoryCache(ignoreCache)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(view)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun loadGif(view: ImageView, gif: Int, placeHolder: Drawable?, ignoreCache: Boolean) {
        try {
            Glide.with(view)
                .asGif()
                .load(gif)
                .placeholder(placeHolder)
                .skipMemoryCache(ignoreCache)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(view)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun loadImageBlur(view: ImageView, url: String?, placeHolder: Drawable?, ignoreCache: Boolean, radius: Int, sampling: Int) {
        try {
            Glide.with(view)
                .load(url)
                .transform(BlurTransformation(radius, sampling))
                .placeholder(placeHolder)
                .skipMemoryCache(ignoreCache)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(view)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
