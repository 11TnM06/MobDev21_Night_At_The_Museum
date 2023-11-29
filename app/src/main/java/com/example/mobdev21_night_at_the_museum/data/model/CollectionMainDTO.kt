package com.example.mobdev21_night_at_the_museum.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CollectionMainDTO(
    @SerializedName("collections")
    @Expose
    var collectionsList: List<CollectionDTO>? = null
)
