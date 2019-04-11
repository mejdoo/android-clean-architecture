package com.mejdoo.clean.data.source.remote.implementation

import com.mejdoo.clean.data.mapper.CommentEntityMapper
import com.mejdoo.clean.data.source.remote.abstraction.CleanApi
import com.mejdoo.clean.data.source.remote.abstraction.CommentRemoteDataSource
import com.mejdoo.clean.domain.model.Comment
import io.reactivex.Single


class CommentRemoteDataSourceImpl constructor(
    private val api: CleanApi,
    private val mapper: CommentEntityMapper
) : CommentRemoteDataSource {


    override fun getCommentsByPostId(postId: Int): Single<List<Comment>> =
        api.getCommentsByPostId(postId)
            .map { mapper.mapListToDomain(it) }


}