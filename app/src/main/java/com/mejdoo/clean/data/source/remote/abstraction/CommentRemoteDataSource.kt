package com.mejdoo.clean.data.source.remote.abstraction

import com.mejdoo.clean.domain.model.Comment

interface CommentRemoteDataSource {
    suspend fun commentsForPost(postId: Int): List<Comment>
}
