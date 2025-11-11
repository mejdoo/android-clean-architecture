package com.mejdoo.clean.presentation.mapper

import com.mejdoo.clean.domain.model.Post
import com.mejdoo.clean.presentation.model.PostItem

// PostItemMappers.kt

fun Post.toPostItem() =
    PostItem(
        postId = id,
        userId = userId,
        title = title,
    )

fun List<Post>.toPostItemList() = map { it.toPostItem() }
