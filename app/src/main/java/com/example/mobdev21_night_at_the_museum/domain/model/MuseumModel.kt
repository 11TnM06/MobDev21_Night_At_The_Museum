package com.example.mobdev21_night_at_the_museum.domain.model


import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MuseumModel(
    var key: String? = null,

    var name: String? = null,

    var thumbnail: String? = null,

    var place: String? = null,

    var description: String? = null,

    var icon: String? = null,
    ) : Parcelable