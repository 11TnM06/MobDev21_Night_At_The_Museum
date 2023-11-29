package com.example.mobdev21_night_at_the_museum.data.repo

import com.example.mobdev21_night_at_the_museum.data.BaseRepo
import com.example.mobdev21_night_at_the_museum.data.IMockService
import com.example.mobdev21_night_at_the_museum.data.convert.CollectionMainDTOConvertToCollectionMain
import com.example.mobdev21_night_at_the_museum.data.convert.ModelMainDTOConvertToModelMain
import com.example.mobdev21_night_at_the_museum.data.invokeApi
import com.example.mobdev21_night_at_the_museum.data.invokeMockService
import com.example.mobdev21_night_at_the_museum.domain.model.MCollection
import com.example.mobdev21_night_at_the_museum.domain.model.Model3d
import com.example.mobdev21_night_at_the_museum.domain.repo.IRemoteRepo

class RemoteRepoImpl : IRemoteRepo, BaseRepo() {
    override fun getListCollections(): List<MCollection> {
        val service = invokeMockService(IMockService::class.java)
        return service.getCollections().invokeApi { _, body ->
            CollectionMainDTOConvertToCollectionMain().convert(body.data!!).collectionsList ?: emptyList()
        }
    }

    override fun getModelInCollection(collectionId: Int): List<Model3d> {
        val service = invokeMockService(IMockService::class.java)
        return service.getModelsInCollection(collectionId).invokeApi { _, body ->
            ModelMainDTOConvertToModelMain().convert(body.data!!).modelsList ?: emptyList()
        }
    }
}
