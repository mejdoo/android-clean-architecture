package com.mejdoo.clean.data.source.local.implementation

import com.mejdoo.clean.data.mapper.toCommentEntity
import com.mejdoo.clean.data.mapper.toCommentList
import com.mejdoo.clean.data.source.local.abstraction.CommentDao
import com.mejdoo.clean.data.source.local.abstraction.CommentLocalDataSource
import com.mejdoo.clean.domain.model.Comment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CommentLocalDataSourceImpl(private val dao: CommentDao) : CommentLocalDataSource {
    override fun commentsForPost(postId: Int): Flow<List<Comment>> = dao
        .commentsForPost(postId)
        .map { it.toCommentList() }

    override suspend fun insertComment(comment: Comment) {
        dao.insertComment(comment.toCommentEntity())
    }
}
