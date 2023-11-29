package com.example.mobdev21_night_at_the_museum.domain.model

import kotlinx.parcelize.Parcelize

@Parcelize
data class FavoriteData(

    var collections: MutableList<SimpleModel?>? = null,

    var items: MutableList<SimpleModel?>? = null,

    var stories: MutableList<SimpleModel?>? = null,

) : MuseumModel()
