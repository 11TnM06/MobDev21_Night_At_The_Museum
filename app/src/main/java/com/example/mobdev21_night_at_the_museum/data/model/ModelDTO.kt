package com.example.mobdev21_night_at_the_museum.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ModelDTO(
    @SerializedName("id")
    @Expose
    var id: Int? = null,

    @SerializedName("name")
    @Expose
    var name: String? = null,

    @SerializedName("thumbnail")
    @Expose
    var thumbnail: String? = null,

    @SerializedName("description")
    @Expose
    var description: String? = null,

    @SerializedName("path")
    @Expose
    var path: String? = null
) {
}
