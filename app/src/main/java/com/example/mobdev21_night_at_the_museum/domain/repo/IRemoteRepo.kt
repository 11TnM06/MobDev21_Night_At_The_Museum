package com.example.mobdev21_night_at_the_museum.domain.repo

import com.example.mobdev21_night_at_the_museum.domain.model.MCollection
import com.example.mobdev21_night_at_the_museum.domain.model.Model3d

interface IRemoteRepo {
    fun getListCollections(): List<MCollection>
    fun getModelInCollection(collectionId: Int): List<Model3d>
}
