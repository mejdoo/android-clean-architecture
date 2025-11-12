package com.mejdoo.clean.domain.repository

import com.mejdoo.clean.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun userById(userId: Int): Flow<User>
}
