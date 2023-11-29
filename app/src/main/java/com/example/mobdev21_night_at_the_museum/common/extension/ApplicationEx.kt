package com.example.mobdev21_night_at_the_museum.common.extension

import android.app.Application

const val BOOLEAN_DEFAULT = false
const val INT_DEFAULT = 0
const val LONG_DEFAULT = 0L
const val DOUBLE_DEFAULT = 0.0
const val FLOAT_DEFAULT = 0f
const val STRING_DEFAULT = ""

private var application: Application? = null

fun setApplication(context: Application) {
    application = context
}

fun getApplication() = application ?: throw RuntimeException("Application context mustn't null")
