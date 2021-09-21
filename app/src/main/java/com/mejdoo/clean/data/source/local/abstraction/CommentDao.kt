package com.mejdoo.clean.data.source.local.abstraction

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.mejdoo.clean.data.model.CommentEntity
import io.reactivex.Single

@Dao
interface CommentDao {
    @Query("SELECT * from comment WHERE postId = :postId")
    fun getCommentsByPostId(postId: Int): Single<List<CommentEntity>>

    @Insert(onConflict = REPLACE)
    fun insertComment(comment: CommentEntity)
}