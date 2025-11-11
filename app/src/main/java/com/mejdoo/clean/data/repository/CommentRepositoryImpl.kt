package com.mejdoo.clean.data.repository

import com.mejdoo.clean.data.source.local.abstraction.CommentLocalDataSource
import com.mejdoo.clean.data.source.remote.abstraction.CommentRemoteDataSource
import com.mejdoo.clean.domain.model.Comment
import com.mejdoo.clean.domain.repository.CommentRepository
import io.reactivex.Single

class CommentRepositoryImpl(
    private val remoteDataSource: CommentRemoteDataSource,
    private val localDataSource: CommentLocalDataSource,
) : CommentRepository {
    override fun commentsForPost(postId: Int): Single<List<Comment>> =
        remoteDataSource
            .commentsForPost(postId)
            .doOnSuccess { it.forEach { comment -> localDataSource.insertComment(comment) } }
            .onErrorResumeNext { localDataSource.commentsForPost(postId) }
}
