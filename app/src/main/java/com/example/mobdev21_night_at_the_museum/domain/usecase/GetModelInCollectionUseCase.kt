package com.example.mobdev21_night_at_the_museum.domain.usecase

import com.example.mobdev21_night_at_the_museum.common.BaseUseCase
import com.example.mobdev21_night_at_the_museum.di.RepositoryFactory
import com.example.mobdev21_night_at_the_museum.domain.model.Model3d

class GetModelInCollectionUseCase : BaseUseCase<GetModelInCollectionUseCase.GetModelInCollectionUseCaseRV, List<Model3d>>() {
    override suspend fun execute(rv: GetModelInCollectionUseCaseRV): List<Model3d> {
        val repo = RepositoryFactory.getRemoteRepo()
        return repo.getModelInCollection(rv.collectionId)
    }

    class GetModelInCollectionUseCaseRV(val collectionId: Int) : RequestValue
}
