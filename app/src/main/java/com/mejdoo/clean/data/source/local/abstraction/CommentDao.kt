package com.mejdoo.clean.data.source.local.abstraction

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.mejdoo.clean.data.model.CommentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CommentDao {
    @Query("SELECT * from comment WHERE postId = :postId")
    fun commentsForPost(postId: Int): Flow<List<CommentEntity>>

    @Insert(onConflict = REPLACE)
    suspend fun insertComment(comment: CommentEntity)
}
