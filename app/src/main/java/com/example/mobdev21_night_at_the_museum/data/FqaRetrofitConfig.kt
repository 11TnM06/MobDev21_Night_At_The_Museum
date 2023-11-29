package com.example.mobdev21_night_at_the_museum.data

import okhttp3.Interceptor

class FqaRetrofitConfig : BaseRetrofitConfig() {
    override fun getUrl(): String {
        return ApiConfig.MOCK_URL
    }

    override fun getInterceptorList(): Array<Interceptor> {
        return arrayOf()
    }
}
