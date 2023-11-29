package com.example.mobdev21_night_at_the_museum.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.example.mobdev21_night_at_the_museum.common.exception.APIException

data class MessageDTO(
    @SerializedName("code")
    @Expose
    var code: String? = null,

    @SerializedName("status")
    @Expose
    var status: String? = null,

    @SerializedName("spec")
    @Expose
    var spec: String? = null,

    @SerializedName("response_time")
    @Expose
    var responseTime: Long? = null
) {
    fun codeToInt(): Int {
        return try {
            code?.toInt() ?: APIException.SERVER_ERROR_CODE_UNDEFINE
        } catch (e: Exception) {
            APIException.SERVER_ERROR_CODE_UNDEFINE
        }
    }
}
