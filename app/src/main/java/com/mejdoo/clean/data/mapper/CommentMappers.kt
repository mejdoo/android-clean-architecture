package com.mejdoo.clean.data.mapper

import com.mejdoo.clean.data.model.CommentEntity
import com.mejdoo.clean.domain.model.Comment

// CommentMappers.kt

fun CommentEntity.toDomain() = Comment(
    postId = postId,
    id = id,
    name = name,
    email = email,
    body = body
)

fun Comment.toData() = CommentEntity(
    postId = postId,
    id = id,
    name = name,
    email = email,
    body = body
)

fun List<CommentEntity>.toDomainList() = map { it.toDomain() }
fun List<Comment>.toDataList() = map { it.toData() }