package com.example.mobdev21_night_at_the_museum.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.example.mobdev21_night_at_the_museum.data.BaseApiResponse

class CollectionResponse : BaseApiResponse() {
    @SerializedName("data")
    @Expose
    var data: CollectionMainDTO? = null
}
