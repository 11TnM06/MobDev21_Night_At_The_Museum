package com.example.mobdev21_night_at_the_museum.presentation.widget

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager


class CustomViewPager constructor(
    context: Context,
    attrs: AttributeSet?
) : ViewPager(context, attrs) {


    init {
        overScrollMode = OVER_SCROLL_NEVER
    }

    private var isPagingEnabled = true

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return isPagingEnabled && super.onTouchEvent(event)
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return isPagingEnabled && super.onInterceptTouchEvent(event)
    }

    fun setPagingEnabled(isEnabled: Boolean) {
        isPagingEnabled = isEnabled
    }

}
