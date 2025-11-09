package com.mejdoo.clean.data.source.remote.implementation

import com.mejdoo.clean.data.mapper.toDomain
import com.mejdoo.clean.data.mapper.toDomainList
import com.mejdoo.clean.data.source.remote.abstraction.CleanApi
import com.mejdoo.clean.data.source.remote.abstraction.PostRemoteDataSource
import com.mejdoo.clean.domain.model.Post
import io.reactivex.Single

class PostRemoteDataSourceImpl(
    private val api: CleanApi
) : PostRemoteDataSource {

    override fun allPosts(): Single<List<Post>> =
        api.allPosts()
            .map { it.toDomainList() }

    override fun postById(postId: Int): Single<Post> =
        api.postById(postId)
            .map { it.toDomain() }

}