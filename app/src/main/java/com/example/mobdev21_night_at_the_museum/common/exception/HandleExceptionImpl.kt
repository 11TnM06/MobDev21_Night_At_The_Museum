package com.example.mobdev21_night_at_the_museum.common.exception

import com.example.mobdev21_night_at_the_museum.R
import com.example.mobdev21_night_at_the_museum.common.extension.getAppString

object HandleExceptionImpl : IHandleException {

    override fun getMessage(exception: BaseException): String? {
        return when (exception) {
            is APIException -> getMessageByAPIException(exception)

            is DBException -> getMessageByDBException(exception)

            else -> null
        }
    }

    private fun getMessageByAPIException(e: APIException): String {
        return when (e.code) {
            APIException.NETWORK_ERROR -> getAppString(R.string.no_network)
            APIException.TIME_OUT_ERROR -> getAppString(R.string.server_time_out)
            APIException.CONVERT_JSON_FROM_RESPONSE_ERROR -> getAppString(R.string.convert_response_error)
            APIException.RESPONSE_BODY_ERROR -> getAppString(R.string.response_error)
            else -> {
                e.message.toString()
            }
        }
    }

    private fun getMessageByDBException(e: DBException): String {
        return when (e.code) {
            else -> e.message.toString()
        }
    }
}

interface IHandleException {
    fun getMessage(exception: BaseException): String?
}
