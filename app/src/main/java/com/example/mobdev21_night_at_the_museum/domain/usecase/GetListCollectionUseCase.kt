package com.example.mobdev21_night_at_the_museum.domain.usecase

import com.example.mobdev21_night_at_the_museum.common.BaseUseCase
import com.example.mobdev21_night_at_the_museum.di.RepositoryFactory
import com.example.mobdev21_night_at_the_museum.domain.model.MCollection

class GetListCollectionUseCase: BaseUseCase<BaseUseCase.VoidRequest, List<MCollection>>() {
    override suspend fun execute(rv: VoidRequest): List<MCollection> {
        val repo = RepositoryFactory.getRemoteRepo()
        return repo.getListCollections()
//        return mockListCollection()
    }
}
