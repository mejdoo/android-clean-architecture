package com.mejdoo.clean.data.source.local.abstraction

import com.mejdoo.clean.domain.model.Post
import kotlinx.coroutines.flow.Flow

interface PostLocalDataSource {
    fun allPosts(): Flow<List<Post>>

    fun postById(postId: Int): Flow<Post>

    suspend fun insertPost(post: Post)
}
