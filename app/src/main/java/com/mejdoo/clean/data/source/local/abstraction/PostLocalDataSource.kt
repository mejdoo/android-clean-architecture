package com.mejdoo.clean.data.source.local.abstraction

import com.mejdoo.clean.domain.model.Post
import io.reactivex.Single

interface PostLocalDataSource {

    fun getAllPosts(): Single<List<Post>>
    fun getPostById(postId: Int): Single<Post>
    fun insertPost(post: Post)
}
