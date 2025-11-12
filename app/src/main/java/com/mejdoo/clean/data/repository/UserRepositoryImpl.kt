package com.mejdoo.clean.data.repository

import com.mejdoo.clean.data.source.local.abstraction.UserLocalDataSource
import com.mejdoo.clean.data.source.remote.abstraction.UserRemoteDataSource
import com.mejdoo.clean.domain.model.User
import com.mejdoo.clean.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onStart

class UserRepositoryImpl(
    private val remoteDataSource: UserRemoteDataSource,
    private val localDataSource: UserLocalDataSource,
) : UserRepository {
    override fun userById(userId: Int): Flow<User> =
        localDataSource.userById(userId).onStart {
            try {
                val user = remoteDataSource.userById(userId)
                localDataSource.insertUser(user)
            } catch (_: Exception) {
                // ignore
            }
        }
}
