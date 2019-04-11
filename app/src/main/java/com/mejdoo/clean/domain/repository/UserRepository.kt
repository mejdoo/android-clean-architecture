package  com.mejdoo.clean.domain.repository

import com.mejdoo.clean.domain.model.User
import io.reactivex.Single


interface UserRepository {


    fun getUserById(userId: Int): Single<User>

}
