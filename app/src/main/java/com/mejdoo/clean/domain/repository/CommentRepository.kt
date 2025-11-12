package com.mejdoo.clean.domain.repository

import com.mejdoo.clean.domain.model.Comment
import kotlinx.coroutines.flow.Flow

interface CommentRepository {
    fun commentsForPost(postId: Int): Flow<List<Comment>>
}
