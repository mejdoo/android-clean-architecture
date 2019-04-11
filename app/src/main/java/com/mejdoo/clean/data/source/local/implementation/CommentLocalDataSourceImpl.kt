package com.mejdoo.clean.data.source.local.implementation

import com.mejdoo.clean.data.mapper.CommentEntityMapper
import com.mejdoo.clean.data.source.local.abstraction.CommentDao
import com.mejdoo.clean.data.source.local.abstraction.CommentLocalDataSource
import com.mejdoo.clean.domain.model.Comment
import io.reactivex.Single


class CommentLocalDataSourceImpl constructor(
    private val dao: CommentDao,
    private val mapper: CommentEntityMapper
) : CommentLocalDataSource {


    override fun getCommentsByPostId(postId: Int): Single<List<Comment>> =
        dao.getCommentsByPostId(postId)
            .map { mapper.mapListToDomain(it) }


    override fun insertComment(comment: Comment) {
        dao.insertComment(mapper.mapFromDomain(comment))
    }

}