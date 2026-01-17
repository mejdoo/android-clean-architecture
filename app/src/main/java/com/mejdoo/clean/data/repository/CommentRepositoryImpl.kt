package com.mejdoo.clean.data.repository

import com.mejdoo.clean.data.source.local.abstraction.CommentLocalDataSource
import com.mejdoo.clean.data.source.remote.abstraction.CommentRemoteDataSource
import com.mejdoo.clean.domain.model.Comment
import com.mejdoo.clean.domain.repository.CommentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onStart

class CommentRepositoryImpl(
    private val remoteDataSource: CommentRemoteDataSource,
    private val localDataSource: CommentLocalDataSource
) : CommentRepository {
    override fun commentsForPost(postId: Int): Flow<List<Comment>> = localDataSource.commentsForPost(postId).onStart {
        try {
            val comments = remoteDataSource.commentsForPost(postId)
            comments.forEach { comment -> localDataSource.insertComment(comment) }
        } catch (_: Exception) {
            // ignore
        }
    }
}
