package com.mejdoo.clean.data.source.local.abstraction

import com.mejdoo.clean.domain.model.Comment
import io.reactivex.Single

interface CommentLocalDataSource {
    fun commentsForPost(postId: Int): Single<List<Comment>>

    fun insertComment(comment: Comment)
}
