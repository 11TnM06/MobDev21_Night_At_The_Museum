package com.example.mobdev21_night_at_the_museum.presentation.intro

import android.graphics.Bitmap
import android.graphics.Point
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.WindowManager
import androidx.core.graphics.drawable.toDrawable
import com.example.mobdev21_night_at_the_museum.R
import com.example.mobdev21_night_at_the_museum.common.binding.MuseumActivity
import com.example.mobdev21_night_at_the_museum.common.extension.getAppDrawable
import com.example.mobdev21_night_at_the_museum.common.extension.setOnSafeClick
import com.example.mobdev21_night_at_the_museum.databinding.IntroductionActivityBinding

class IntroductionActivity : MuseumActivity<IntroductionActivityBinding>(R.layout.introduction_activity) {
    var introBg: Drawable? = null

    override fun onInitView() {
        super.onInitView()
        initOnClick()
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        setIntroBackground()
    }

    private fun initOnClick() {
        binding.mcvIntroductionSignIn.setOnSafeClick {
            navigateTo(LoginActivity::class.java)
        }

        binding.mcvIntroductionSignUp.setOnSafeClick {
            navigateTo(SignUpActivity::class.java)
        }
    }

    fun setWhiteBackground() {
        binding.constIntroductionRoot.background = getAppDrawable(R.color.white)
    }

    fun setIntroBackground() {
        if (introBg == null) {
            getAppDrawable(R.drawable.intro_bg)?.let {
                introBg = cropDrawable(it, getScreenRatio())
            }
        }
        binding.constIntroductionRoot.background = introBg
    }

    private fun cropDrawable(drawable: Drawable, ratio: Float): Drawable {
        val originalBitmap = (drawable as BitmapDrawable).bitmap

        val width = originalBitmap.width
        val height = originalBitmap.height
        return if (width > height * ratio) {
            val cropWidth = (height * ratio).toInt()
            val cropOffset = (width - cropWidth) / 2
            val croppedBitmap = Bitmap.createBitmap(originalBitmap, cropOffset, 0, cropWidth, height)
            croppedBitmap.toDrawable(resources)
        } else {
            val cropHeight = (width / ratio).toInt()
            val cropOffset = (height - cropHeight) / 2
            val croppedBitmap = Bitmap.createBitmap(originalBitmap, 0, cropOffset, width, cropHeight)
            croppedBitmap.toDrawable(resources)
        }
    }

    private fun getScreenRatio(): Float {
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        return size.x.toFloat() / size.y.toFloat()
    }
}
