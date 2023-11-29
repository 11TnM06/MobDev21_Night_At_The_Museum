package com.example.mobdev21_night_at_the_museum.di

import com.example.mobdev21_night_at_the_museum.data.repo.FirebaseRepoImpl
import com.example.mobdev21_night_at_the_museum.data.repo.RemoteRepoImpl
import com.example.mobdev21_night_at_the_museum.domain.repo.IFirebaseRepo
import com.example.mobdev21_night_at_the_museum.domain.repo.IRemoteRepo

object RepositoryFactory {
    private val firebaseRepoImpl = FirebaseRepoImpl()
    private val remoteRepoImpl = RemoteRepoImpl()

    fun getFirebaseRepo(): IFirebaseRepo {
        return firebaseRepoImpl
    }

    fun getRemoteRepo(): IRemoteRepo {
        return remoteRepoImpl
    }
}
