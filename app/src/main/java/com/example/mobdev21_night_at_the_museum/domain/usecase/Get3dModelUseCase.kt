package com.example.mobdev21_night_at_the_museum.domain.usecase

import com.example.mobdev21_night_at_the_museum.common.BaseUseCase
import com.example.mobdev21_night_at_the_museum.di.RepositoryFactory
import java.io.File

class Get3dModelUseCase : BaseUseCase<Get3dModelUseCase.Get3dModelUseCaseRV, File>() {
    override suspend fun execute(rv: Get3dModelUseCaseRV): File {
        val repo = RepositoryFactory.getFirebaseRepo()
        if (rv.name == null) {
            throw IllegalArgumentException("model name is null")
        }
        return repo.get3DModel(rv.name)
    }

    class Get3dModelUseCaseRV(val name: String?) : RequestValue
}
