package com.mejdoo.clean.data.source.remote.abstraction

import com.mejdoo.clean.domain.model.Post

interface PostRemoteDataSource {
    suspend fun allPosts(): List<Post>

    suspend fun postById(postId: Int): Post
}
