package com.mejdoo.clean.data.mapper

import com.mejdoo.clean.data.model.PostEntity
import com.mejdoo.clean.domain.model.Post

// PostMappers.kt

fun PostEntity.toDomain() = Post(
    userId = userId,
    id = id,
    title = title,
    body = body
)

fun Post.toData() = PostEntity(
    userId = userId,
    id = id,
    title = title,
    body = body
)

fun List<PostEntity>.toDomainList() = map { it.toDomain() }
fun List<Post>.toDataList() = map { it.toData() }