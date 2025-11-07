package com.mejdoo.clean.data.source.local.implementation

import com.mejdoo.clean.data.mapper.toData
import com.mejdoo.clean.data.mapper.toDomain
import com.mejdoo.clean.data.source.local.abstraction.UserDao
import com.mejdoo.clean.data.source.local.abstraction.UserLocalDataSource
import com.mejdoo.clean.domain.model.User
import io.reactivex.Single

class UserLocalDataSourceImpl(
    private val dao: UserDao
) : UserLocalDataSource {

    override fun getUserById(userId: Int): Single<User> =
        dao.getUserById(userId)
            .map { it.toDomain() }

    override fun insertUser(user: User) {
        dao.insertUser(user.toData())
    }
}