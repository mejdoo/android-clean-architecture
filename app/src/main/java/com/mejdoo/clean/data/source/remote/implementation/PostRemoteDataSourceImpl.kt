package com.mejdoo.clean.data.source.remote.implementation

import com.mejdoo.clean.data.mapper.toPost
import com.mejdoo.clean.data.mapper.toPostList
import com.mejdoo.clean.data.source.remote.abstraction.CleanApi
import com.mejdoo.clean.data.source.remote.abstraction.PostRemoteDataSource
import com.mejdoo.clean.domain.model.Post

class PostRemoteDataSourceImpl(private val api: CleanApi) : PostRemoteDataSource {
    override suspend fun allPosts(): List<Post> = api.allPosts().toPostList()

    override suspend fun postById(postId: Int): Post = api.postById(postId).toPost()
}
