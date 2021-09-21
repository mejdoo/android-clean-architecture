package com.mejdoo.clean.data.source.remote.implementation

import com.mejdoo.clean.data.mapper.PostEntityMapper
import com.mejdoo.clean.data.source.remote.abstraction.CleanApi
import com.mejdoo.clean.data.source.remote.abstraction.PostRemoteDataSource
import com.mejdoo.clean.domain.model.Post
import io.reactivex.Single

class PostRemoteDataSourceImpl constructor(
        private val api: CleanApi,
        private val mapper: PostEntityMapper
) : PostRemoteDataSource {

    override fun getAllPosts(): Single<List<Post>> =
            api.getAllPosts()
                    .map { mapper.mapListToDomain(it) }

    override fun getPostById(postId: Int): Single<Post> =
            api.getPostById(postId)
                    .map { mapper.mapToDomain(it) }

}