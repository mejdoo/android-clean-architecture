package com.mejdoo.clean.data.source.remote.abstraction

import com.mejdoo.clean.domain.model.Post
import io.reactivex.Single

interface PostRemoteDataSource {

    fun getAllPosts(): Single<List<Post>>
    fun getPostById(postId: Int): Single<Post>
}
