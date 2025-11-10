package com.mejdoo.clean.data.source.remote.implementation

import com.mejdoo.clean.data.mapper.toPost
import com.mejdoo.clean.data.mapper.toPostList
import com.mejdoo.clean.data.source.remote.abstraction.CleanApi
import com.mejdoo.clean.data.source.remote.abstraction.PostRemoteDataSource
import com.mejdoo.clean.domain.model.Post
import io.reactivex.Single

class PostRemoteDataSourceImpl(
    private val api: CleanApi
) : PostRemoteDataSource {

    override fun allPosts(): Single<List<Post>> =
        api.allPosts()
            .map { it.toPostList() }

    override fun postById(postId: Int): Single<Post> =
        api.postById(postId)
            .map { it.toPost() }

}