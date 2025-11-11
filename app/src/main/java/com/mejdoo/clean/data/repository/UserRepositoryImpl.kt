package com.mejdoo.clean.data.repository

import com.mejdoo.clean.data.source.local.abstraction.UserLocalDataSource
import com.mejdoo.clean.data.source.remote.abstraction.UserRemoteDataSource
import com.mejdoo.clean.domain.model.User
import com.mejdoo.clean.domain.repository.UserRepository
import io.reactivex.Single

class UserRepositoryImpl(
    private val remoteDataSource: UserRemoteDataSource,
    private val localDataSource: UserLocalDataSource,
) : UserRepository {
    override fun userById(userId: Int): Single<User> =
        remoteDataSource
            .userById(userId)
            .doOnSuccess { localDataSource.insertUser(it) }
            .onErrorResumeNext { localDataSource.userById(userId) }
}
