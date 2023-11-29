package com.example.mobdev21_night_at_the_museum.presentation

import android.annotation.SuppressLint
import com.example.mobdev21_night_at_the_museum.R
import com.example.mobdev21_night_at_the_museum.common.binding.MuseumFragment
import com.example.mobdev21_night_at_the_museum.common.extension.loadImage
import com.example.mobdev21_night_at_the_museum.databinding.ZoomFragmentBinding

class ZoomFragment : MuseumFragment<ZoomFragmentBinding>(R.layout.zoom_fragment) {
    companion object {
        const val IMAGE_URL_KEY = "IMAGE_URL_KEY"
        const val ORIGIN_SCALE_FACTOR = 1.5f
    }

    private var imageUrl: String? = null
    private var preIsScroll = false

    override fun onPrepareInitView() {
        super.onPrepareInitView()
        imageUrl = arguments?.getString(IMAGE_URL_KEY)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onInitView() {
        super.onInitView()
        realMainActivity.apply {
            preIsScroll = enableScrollHideActionBar(false)
            expandAppBar()
            setBackIcon()

        }
        binding.ivZoom.apply {
            loadImage(imageUrl)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        realMainActivity.enableScrollHideActionBar(preIsScroll)
    }
}
