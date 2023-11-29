package com.example.mobdev21_night_at_the_museum.common

import android.text.Spannable
import android.text.SpannableStringBuilder

internal class SpannableBuilder(text: String? = null) {

    var spannedText: SpannableStringBuilder? = null
    private var mLastStartSpanIndex = 0

    init {
        spannedText = if (text != null) {
            SpannableStringBuilder(text)
        } else {
            SpannableStringBuilder("")
        }
    }

    fun appendText(text: String?): SpannableBuilder {
        mLastStartSpanIndex = spannedText!!.length
        spannedText?.append(text ?: "")
        return this
    }

    fun withSpan(span: Any?): SpannableBuilder {
        val endIndex = spannedText!!.length
        spannedText?.setSpan(
            span, mLastStartSpanIndex, endIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return this
    }


}
