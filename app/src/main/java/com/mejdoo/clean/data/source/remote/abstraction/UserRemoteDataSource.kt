package com.mejdoo.clean.data.source.remote.abstraction

import com.mejdoo.clean.domain.model.User

interface UserRemoteDataSource {
    suspend fun userById(userId: Int): User
}
