package com.example.mobdev21_night_at_the_museum.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class BaseApiResponse : IApiResponse {
    @SerializedName("msg")
    @Expose
    var msg: MessageDTO? = null

    override fun isSuccessful(): Boolean {
        return msg != null && msg?.code == "2000"
    }
}

interface IApiResponse {
    fun isSuccessful(): Boolean
}
