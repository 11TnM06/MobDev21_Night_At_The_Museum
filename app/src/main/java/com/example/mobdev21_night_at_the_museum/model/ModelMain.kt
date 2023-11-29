package com.example.mobdev21_night_at_the_museum.domain.model

import kotlinx.parcelize.Parcelize

@Parcelize
data class ModelMain(

    var description: String? = null,

    var modelsList: List<Model3d>? = null

) : MuseumModel()
