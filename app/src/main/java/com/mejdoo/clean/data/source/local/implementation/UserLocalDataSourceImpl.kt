package com.mejdoo.clean.data.source.local.implementation

import com.mejdoo.clean.data.mapper.UserEntityMapper
import com.mejdoo.clean.data.source.local.abstraction.UserDao
import com.mejdoo.clean.data.source.local.abstraction.UserLocalDataSource
import com.mejdoo.clean.domain.model.User
import io.reactivex.Single

class UserLocalDataSourceImpl constructor(
        private val dao: UserDao,
        private val mapper: UserEntityMapper
) : UserLocalDataSource {

    override fun getUserById(userId: Int): Single<User> =
            dao.getUserById(userId)
                    .map { mapper.mapToDomain(it) }

    override fun insertUser(user: User) {
        dao.insertUser(mapper.mapFromDomain(user))
    }
}