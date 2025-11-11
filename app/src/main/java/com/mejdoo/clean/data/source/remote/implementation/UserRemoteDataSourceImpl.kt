package com.mejdoo.clean.data.source.remote.implementation

import com.mejdoo.clean.data.mapper.toUser
import com.mejdoo.clean.data.source.remote.abstraction.CleanApi
import com.mejdoo.clean.data.source.remote.abstraction.UserRemoteDataSource
import com.mejdoo.clean.domain.model.User
import io.reactivex.Single

class UserRemoteDataSourceImpl(
    private val api: CleanApi,
) : UserRemoteDataSource {
    override fun userById(userId: Int): Single<User> =
        api.userById(userId)
            .map { it.toUser() }
}
