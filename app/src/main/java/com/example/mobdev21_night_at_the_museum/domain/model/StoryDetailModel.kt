package com.example.mobdev21_night_at_the_museum.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StoryDetailModel(
    var description: String? = null,

    var thumbnail: String? = null
) : Parcelable
