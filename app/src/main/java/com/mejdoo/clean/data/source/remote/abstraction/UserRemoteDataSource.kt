package com.mejdoo.clean.data.source.remote.abstraction

import com.mejdoo.clean.domain.model.User
import io.reactivex.Single

interface UserRemoteDataSource {
    fun getUserById(userId: Int): Single<User>
}
