package com.example.mobdev21_night_at_the_museum.domain.model

import com.example.mobdev21_night_at_the_museum.AppPreferences
import kotlinx.parcelize.Parcelize

@Parcelize
data class MCollection(

    var name: String? = null,

    var thumbnail: String? = null,

    var place: String? = null,

    var description: String? = null,

    var icon: String? = null,

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
        isLiked = AppPreferences.getUserInfo().fcollections?.contains(key) ?: false
    }

}
