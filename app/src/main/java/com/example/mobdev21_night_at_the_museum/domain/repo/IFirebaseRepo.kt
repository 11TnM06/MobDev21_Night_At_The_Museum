package com.example.mobdev21_night_at_the_museum.domain.repo

import java.io.File

interface IFirebaseRepo {
    fun get3DModel(name: String): File
}
