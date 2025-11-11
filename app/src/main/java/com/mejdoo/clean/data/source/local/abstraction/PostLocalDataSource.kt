package com.mejdoo.clean.data.source.local.abstraction

import com.mejdoo.clean.domain.model.Post
import io.reactivex.Single

interface PostLocalDataSource {
    fun allPosts(): Single<List<Post>>

    fun postById(postId: Int): Single<Post>

    fun insertPost(post: Post)
}
