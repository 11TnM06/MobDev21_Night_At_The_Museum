package com.example.mobdev21_night_at_the_museum.domain.model

import kotlinx.parcelize.Parcelize

@Parcelize
data class Creator(

    var name: String? = null

) : MuseumModel()
