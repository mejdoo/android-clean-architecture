package  com.mejdoo.clean.data.repository


import com.mejdoo.clean.data.source.local.abstraction.UserLocalDataSource
import com.mejdoo.clean.data.source.remote.abstraction.UserRemoteDataSource
import com.mejdoo.clean.domain.model.User
import com.mejdoo.clean.domain.repository.UserRepository
import io.reactivex.Single

class UserRepositoryImpl constructor(
        private val remoteDataSource: UserRemoteDataSource,
        private val localDataSource: UserLocalDataSource
) : UserRepository {

    override fun getUserById(userId: Int): Single<User> =
            remoteDataSource.getUserById(userId)
                    .doOnSuccess { localDataSource.insertUser(it) }
                    .onErrorResumeNext { localDataSource.getUserById(userId) }

}


