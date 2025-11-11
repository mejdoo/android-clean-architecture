package com.mejdoo.clean.data.mapper

import com.mejdoo.clean.data.model.PostEntity
import com.mejdoo.clean.domain.model.Post

// PostMappers.kt

fun PostEntity.toPost() =
    Post(
        userId = userId,
        id = id,
        title = title,
        body = body,
    )

fun Post.toPostEntity() =
    PostEntity(
        userId = userId,
        id = id,
        title = title,
        body = body,
    )

fun List<PostEntity>.toPostList() = map { it.toPost() }
fun List<Post>.toPostEntityList() = map { it.toPostEntity() }
