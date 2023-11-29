package com.example.mobdev21_night_at_the_museum.data

import com.example.mobdev21_night_at_the_museum.data.model.CollectionResponse
import com.example.mobdev21_night_at_the_museum.data.model.ModelInCollectionResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IMockService : IApiService {
    @GET("collections")
    fun getCollections(): Call<CollectionResponse>

    @GET("collections/model")
    fun getModelsInCollection(@Query("id") collectionId: Int): Call<ModelInCollectionResponse>

}
