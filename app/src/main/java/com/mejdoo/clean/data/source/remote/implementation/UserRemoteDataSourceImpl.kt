package com.mejdoo.clean.data.source.remote.implementation

import com.mejdoo.clean.data.mapper.toUser
import com.mejdoo.clean.data.source.remote.abstraction.CleanApi
import com.mejdoo.clean.data.source.remote.abstraction.UserRemoteDataSource
import com.mejdoo.clean.domain.model.User

class UserRemoteDataSourceImpl(
    private val api: CleanApi,
) : UserRemoteDataSource {
    override suspend fun userById(userId: Int): User = api.userById(userId).toUser()
}
