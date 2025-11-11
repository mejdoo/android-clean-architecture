package com.mejdoo.clean.data.source.local.implementation

import com.mejdoo.clean.data.mapper.toUser
import com.mejdoo.clean.data.mapper.toUserEntity
import com.mejdoo.clean.data.source.local.abstraction.UserDao
import com.mejdoo.clean.data.source.local.abstraction.UserLocalDataSource
import com.mejdoo.clean.domain.model.User
import io.reactivex.Single

class UserLocalDataSourceImpl(
    private val dao: UserDao
) : UserLocalDataSource {

    override fun userById(userId: Int): Single<User> =
        dao.userById(userId)
            .map { it.toUser() }

    override fun insertUser(user: User) {
        dao.insertUser(user.toUserEntity())
    }
}
