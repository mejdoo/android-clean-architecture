package com.mejdoo.clean.data.source.remote.abstraction

import com.mejdoo.clean.domain.model.Post
import io.reactivex.Single

interface PostRemoteDataSource {
    fun allPosts(): Single<List<Post>>

    fun postById(postId: Int): Single<Post>
}
