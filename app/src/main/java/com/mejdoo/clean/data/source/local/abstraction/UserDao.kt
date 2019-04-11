package com.mejdoo.clean.data.source.local.abstraction

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.mejdoo.clean.data.model.UserEntity
import io.reactivex.Single


@Dao
interface UserDao {

    @Query("SELECT * from user WHERE id = :userId")
    fun getUserById(userId: Int): Single<UserEntity>

    @Insert(onConflict = REPLACE)
    fun insertUser(user: UserEntity)
}