package com.example.mobdev21_night_at_the_museum.common.view

import android.view.WindowManager
import androidx.annotation.ColorRes

const val LAYOUT_INVALID = -1

class StatusBar(@ColorRes var color: Int = android.R.color.transparent, var isDarkText: Boolean = true)

class DialogScreen(
    var mode: DIALOG_MODE = DIALOG_MODE.NORMAL,
    var isFullWidth: Boolean = false,
    var isFullHeight: Boolean = false,
    var isDismissByTouchOutSide: Boolean = true,
    var isDismissByOnBackPressed: Boolean = true
) {
    enum class DIALOG_MODE {
        SCALE,
        NORMAL,
        BOTTOM
    }
}

enum class ADJUST_RESIZE_MODE(val value: Int) {
    SOFT_INPUT_ADJUST_NOTHING(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING),
    SOFT_INPUT_ADJUST_RESIZE(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE),
    SOFT_INPUT_STATE_VISIBLE(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE),
}
