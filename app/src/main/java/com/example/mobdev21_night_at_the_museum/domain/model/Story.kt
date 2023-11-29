package com.example.mobdev21_night_at_the_museum.domain.model

import com.example.mobdev21_night_at_the_museum.AppPreferences
import kotlinx.parcelize.Parcelize

@Parcelize
data class Story(

    var title: String? = null,

    var description: String? = null,

    var thumbnail: String? = null,

    var collectionId: String? = null,

    var pages: List<Page>? = null,

    @Deprecated("Use fun safeIsLiked() key instead")
    var isLiked: Boolean? = null

) : MuseumModel() {
    fun safeIsLiked(): Boolean {
        if (isLiked == null) {
            mapIsLiked()
        }
        return isLiked ?: false
    }

    fun mapIsLiked() {
        isLiked = AppPreferences.getUserInfo().fstories?.contains(key) ?: false
    }
}

@Parcelize
data class Page(

    var description: String? = null,

    var thumbnail: String? = null

) : MuseumModel()
