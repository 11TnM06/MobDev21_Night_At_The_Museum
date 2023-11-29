package com.example.mobdev21_night_at_the_museum.domain.model

import kotlinx.parcelize.Parcelize

@Parcelize
data class CollectionMain(

    var collectionsList: List<MCollection>? = null

) : MuseumModel()
