package com.mejdoo.clean.data.source.local.abstraction

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.mejdoo.clean.data.model.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * from user WHERE id = :userId")
    fun userById(userId: Int): Flow<UserEntity>

    @Insert(onConflict = REPLACE)
    suspend fun insertUser(user: UserEntity)
}
