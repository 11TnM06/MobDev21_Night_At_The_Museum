package com.example.mobdev21_night_at_the_museum.domain.model

import kotlinx.parcelize.Parcelize

@Parcelize
class HomeData(

    var streetViews: List<StreetView>? = null,

    var collections: List<MCollection>? = null

) : MuseumModel()
