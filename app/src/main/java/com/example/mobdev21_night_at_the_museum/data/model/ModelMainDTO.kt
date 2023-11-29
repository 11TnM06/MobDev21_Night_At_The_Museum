package com.example.mobdev21_night_at_the_museum.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ModelMainDTO(
    @SerializedName("description")
    @Expose
    var description: String? = null,

    @SerializedName("models")
    @Expose
    var modelsList: List<ModelDTO>? = null
)
