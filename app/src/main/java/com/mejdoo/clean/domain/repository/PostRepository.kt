package com.mejdoo.clean.domain.repository

import com.mejdoo.clean.domain.model.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    fun allPosts(): Flow<List<Post>>

    fun postById(postId: Int): Flow<Post>
}
