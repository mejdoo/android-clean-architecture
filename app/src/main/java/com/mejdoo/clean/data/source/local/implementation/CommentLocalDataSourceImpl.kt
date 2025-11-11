package com.mejdoo.clean.data.source.local.implementation

import com.mejdoo.clean.data.mapper.toCommentEntity
import com.mejdoo.clean.data.mapper.toCommentList
import com.mejdoo.clean.data.source.local.abstraction.CommentDao
import com.mejdoo.clean.data.source.local.abstraction.CommentLocalDataSource
import com.mejdoo.clean.domain.model.Comment
import io.reactivex.Single

class CommentLocalDataSourceImpl(
    private val dao: CommentDao,
) : CommentLocalDataSource {
    override fun commentsForPost(postId: Int): Single<List<Comment>> =
        dao.commentsForPost(postId)
            .map { it.toCommentList() }

    override fun insertComment(comment: Comment) {
        dao.insertComment(comment.toCommentEntity())
    }
}
