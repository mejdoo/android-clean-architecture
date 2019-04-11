package com.mejdoo.clean.data.source.remote.abstraction

import com.mejdoo.clean.domain.model.Comment
import io.reactivex.Single

interface CommentRemoteDataSource {

    fun getCommentsByPostId(postId: Int): Single<List<Comment>>
}
