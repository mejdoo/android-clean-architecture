package com.mejdoo.clean.data.source.remote.implementation

import com.mejdoo.clean.data.mapper.toCommentList
import com.mejdoo.clean.data.source.remote.abstraction.CleanApi
import com.mejdoo.clean.data.source.remote.abstraction.CommentRemoteDataSource
import com.mejdoo.clean.domain.model.Comment
import io.reactivex.Single

class CommentRemoteDataSourceImpl(
    private val api: CleanApi
) : CommentRemoteDataSource {

    override fun commentsForPost(postId: Int): Single<List<Comment>> =
        api.commentsForPost(postId)
            .map { it.toCommentList() }

}