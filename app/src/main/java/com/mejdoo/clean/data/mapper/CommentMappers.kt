package com.mejdoo.clean.data.mapper

import com.mejdoo.clean.data.model.CommentEntity
import com.mejdoo.clean.domain.model.Comment

// CommentMappers.kt

fun CommentEntity.toComment() = Comment(
    postId = postId,
    id = id,
    name = name,
    email = email,
    body = body
)

fun Comment.toCommentEntity() = CommentEntity(
    postId = postId,
    id = id,
    name = name,
    email = email,
    body = body
)

fun List<CommentEntity>.toCommentList() = map { it.toComment() }
fun List<Comment>.toCommentEntityList() = map { it.toCommentEntity() }