package com.mejdoo.clean.data.source.local.abstraction

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.mejdoo.clean.data.model.PostEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {
    @Query("SELECT * from post")
    fun allPosts(): Flow<List<PostEntity>>

    @Query("SELECT * from post WHERE id = :postId")
    fun postById(postId: Int): Flow<PostEntity>

    @Insert(onConflict = REPLACE)
    suspend fun insertPost(post: PostEntity)
}
