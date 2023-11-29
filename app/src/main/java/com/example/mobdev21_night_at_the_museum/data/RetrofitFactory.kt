package com.example.mobdev21_night_at_the_museum.data

import com.example.mobdev21_night_at_the_museum.common.exception.APIException
import retrofit2.Retrofit
import java.util.concurrent.ConcurrentHashMap

object RetrofitFactory {
    private val TAG = RetrofitFactory::class.java.simpleName
    private const val MOCK = "MOCK"

    private val builderMap = ConcurrentHashMap<String, RetrofitBuilderInfo>()

    fun <T> createMockService(service: Class<T>): T {
        synchronized(RetrofitBuilderInfo::class.java) {
            var builderInfo = RetrofitBuilderInfo()
            builderInfo.builder = FqaRetrofitConfig().getRetrofitBuilder()
            builderMap[MOCK] = builderInfo
            val serviceApi = builderInfo.builder?.build()?.create(service)
            return serviceApi ?: throw APIException(APIException.CREATE_INSTANCE_SERVICE_ERROR)
        }
    }

    class RetrofitBuilderInfo {
        var builder: Retrofit.Builder? = null
        var accessToken: String? = null

        fun valid(accessToken: String?): Boolean {
            return this.accessToken == accessToken
        }
    }
}
