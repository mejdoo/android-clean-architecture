package com.mejdoo.clean.data.source.local.abstraction

import com.mejdoo.clean.domain.model.Comment
import kotlinx.coroutines.flow.Flow

interface CommentLocalDataSource {
    fun commentsForPost(postId: Int): Flow<List<Comment>>

    suspend fun insertComment(comment: Comment)
}
