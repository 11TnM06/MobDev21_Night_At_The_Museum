package com.example.mobdev21_night_at_the_museum.data

import com.google.gson.Gson
import com.example.mobdev21_night_at_the_museum.common.exception.APIException
import retrofit2.Response

object ExceptionHelper {
    // INFORMATION

    // SUCCESSFUL
    private const val HTTP_CODE_SUCCESSFUL_OK = 200

    // REDIRECTION

    // CLIENT ERROR

    // SERVER ERROR

    fun throwException(response: Response<*>): APIException {
        return when (response.code()) {
            HTTP_CODE_SUCCESSFUL_OK -> getAPIExceptionWhenHTTPCodeSuccessful(response)
            else -> getAPIExceptionWhenHTTPCodeUnsuccessful(response)
        }
    }

    private fun getAPIExceptionWhenHTTPCodeSuccessful(response: Response<*>): APIException {
        val body = response.body()
        if (body != null) {
            val apiResponse = body as? BaseApiResponse
            val msg = apiResponse?.msg?.spec
            val code = apiResponse?.msg?.codeToInt() ?: APIException.SERVER_ERROR_CODE_UNDEFINE
            return APIException(code, msg)
        }
        return APIException(APIException.RESPONSE_BODY_ERROR)
    }

    private fun getAPIExceptionWhenHTTPCodeUnsuccessful(response: Response<*>): APIException {
        val errorBody = response.errorBody()
        if (errorBody != null) {
            val errorResponse = Gson().fromJson(errorBody.charStream(), BaseApiResponse::class.java)
            var msg = errorResponse?.msg?.spec
            if (msg == null) {
                msg = response.message()
            }
            val code = errorResponse?.msg?.codeToInt() ?: response.code()
            return APIException(code, msg)
        }
        return APIException(response.code(), response.message())
    }
}
