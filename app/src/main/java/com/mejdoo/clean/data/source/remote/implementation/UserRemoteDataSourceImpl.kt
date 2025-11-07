package com.mejdoo.clean.data.source.remote.implementation

import com.mejdoo.clean.data.mapper.toDomain
import com.mejdoo.clean.data.source.remote.abstraction.CleanApi
import com.mejdoo.clean.data.source.remote.abstraction.UserRemoteDataSource
import com.mejdoo.clean.domain.model.User
import io.reactivex.Single

class UserRemoteDataSourceImpl(
    private val api: CleanApi
) : UserRemoteDataSource {

    override fun getUserById(userId: Int): Single<User> =
        api.getUserById(userId)
            .map { it.toDomain() }

}