package com.example.mobdev21_night_at_the_museum.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CollectionDTO(
    @SerializedName("id")
    @Expose
    var id: Int? = null,

    @SerializedName("name")
    @Expose
    var name: String? = null,

    @SerializedName("image")
    @Expose
    var thumbnail: String? = null,
) {
}
