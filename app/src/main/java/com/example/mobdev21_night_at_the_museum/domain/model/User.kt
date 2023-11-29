package com.example.mobdev21_night_at_the_museum.domain.model

import kotlinx.parcelize.Parcelize

@Parcelize
data class User(

    var name: String? = null,

    var email: String? = null,

    var password: String? = null,

    var avatar: String? = null,

    var fitems: List<String>? = null,

    var fcollections: List<String>? = null,

    var fstories: List<String>? = null

) : MuseumModel()
