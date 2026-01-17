package com.mejdoo.clean.presentation.mapper

import com.mejdoo.clean.domain.usecase.CombinedPostUserComments
import com.mejdoo.clean.presentation.model.PostDetail

// PostDetailMappers.kt

fun CombinedPostUserComments.toPostDetail() = PostDetail(
    postId = post.id,
    userId = user.id,
    title = post.title,
    body = post.body,
    userName = user.name,
    commentCount = comments.size
)

fun List<CombinedPostUserComments>.toPostDetailList() = map { it.toPostDetail() }
