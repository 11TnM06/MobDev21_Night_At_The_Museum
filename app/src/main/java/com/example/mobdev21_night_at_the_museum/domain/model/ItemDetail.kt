package com.example.mobdev21_night_at_the_museum.domain.model

import kotlinx.parcelize.Parcelize

@Parcelize
data class ItemDetail(

    var title: String? = null,

    var description: String? = null

) : MuseumModel() {

    // from Title: Jason to title = "Title:" and description = "Jason"
    fun mapFrom(string: String) {
        val split = string.split(":")
        title = split[0] + ":"
        description = split[1].trim()
    }
}
