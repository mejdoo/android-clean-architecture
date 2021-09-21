package com.mejdoo.clean.data.source.remote.implementation

import com.mejdoo.clean.data.mapper.UserEntityMapper
import com.mejdoo.clean.data.source.remote.abstraction.CleanApi
import com.mejdoo.clean.data.source.remote.abstraction.UserRemoteDataSource
import com.mejdoo.clean.domain.model.User
import io.reactivex.Single

class UserRemoteDataSourceImpl constructor(
        private val api: CleanApi,
        private val mapper: UserEntityMapper
) : UserRemoteDataSource {

    override fun getUserById(userId: Int): Single<User> =
            api.getUserById(userId)
                    .map { mapper.mapToDomain(it) }

}