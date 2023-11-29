package com.example.mobdev21_night_at_the_museum.data

import com.example.mobdev21_night_at_the_museum.common.exception.APIException
import okhttp3.Headers
import retrofit2.Call
import java.io.EOFException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun <RESPONSE : IApiResponse, RETURN_VALUE> Call<RESPONSE>.invokeApi(
    block: (Headers, RESPONSE) -> RETURN_VALUE
): RETURN_VALUE {
    try {
        val response = this.execute()
        if (response.isSuccessful) {
            val body: RESPONSE? = response.body()
            if (body != null) {
                if (body.isSuccessful()) {
                    return block(response.headers(), body)
                }
            }
        }
        throw ExceptionHelper.throwException(response)
    } catch (e: Exception) {
        when (e) {
            is UnknownHostException -> throw APIException(APIException.NETWORK_ERROR)
            is SocketTimeoutException -> throw APIException(APIException.TIME_OUT_ERROR)
            is EOFException -> throw APIException(APIException.CONVERT_JSON_FROM_RESPONSE_ERROR)
            else -> throw e
        }
    }
}

fun <T : IApiService> BaseRepo.invokeMockService(service: Class<T>): T {
    return RetrofitFactory.createMockService(service)
}
