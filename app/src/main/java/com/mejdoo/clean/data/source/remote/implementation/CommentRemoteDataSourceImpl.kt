package com.mejdoo.clean.data.source.remote.implementation

import com.mejdoo.clean.data.mapper.toCommentList
import com.mejdoo.clean.data.source.remote.abstraction.CleanApi
import com.mejdoo.clean.data.source.remote.abstraction.CommentRemoteDataSource
import com.mejdoo.clean.domain.model.Comment

class CommentRemoteDataSourceImpl(
    private val api: CleanApi,
) : CommentRemoteDataSource {
    override suspend fun commentsForPost(postId: Int): List<Comment> =
        api.commentsForPost(postId).toCommentList()
}
