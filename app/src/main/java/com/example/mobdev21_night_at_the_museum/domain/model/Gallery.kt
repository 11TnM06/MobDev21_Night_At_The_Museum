package com.example.mobdev21_night_at_the_museum.domain.model

import com.google.firebase.Timestamp
import kotlinx.parcelize.Parcelize

@Parcelize
data class Gallery(

    var title: String? = null,

    var description: String? = null,

    var items: List<Item>? = null,

    var idOfItems: List<String>? = null,

    var createTime: Timestamp? = null

) : MuseumModel()
