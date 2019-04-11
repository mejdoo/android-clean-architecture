package com.mejdoo.clean.data.source.local.abstraction

import com.mejdoo.clean.domain.model.User
import io.reactivex.Single

interface UserLocalDataSource {

    fun getUserById(userId: Int): Single<User>
    fun insertUser(user: User)
}
