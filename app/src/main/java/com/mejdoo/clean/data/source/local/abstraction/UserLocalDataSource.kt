package com.mejdoo.clean.data.source.local.abstraction

import com.mejdoo.clean.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserLocalDataSource {
    fun userById(userId: Int): Flow<User>

    suspend fun insertUser(user: User)
}
