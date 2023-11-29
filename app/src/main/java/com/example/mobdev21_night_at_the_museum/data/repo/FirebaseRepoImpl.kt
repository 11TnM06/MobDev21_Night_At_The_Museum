package com.example.mobdev21_night_at_the_museum.data.repo

import com.example.mobdev21_night_at_the_museum.domain.repo.IFirebaseRepo
import java.io.File

class FirebaseRepoImpl : IFirebaseRepo {
    override fun get3DModel(name: String): File {
        return File("")
    }
}
