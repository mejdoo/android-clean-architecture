package com.mejdoo.clean.data.source.local.implementation

import com.mejdoo.clean.data.mapper.toData
import com.mejdoo.clean.data.mapper.toDomain
import com.mejdoo.clean.data.mapper.toDomainList
import com.mejdoo.clean.data.source.local.abstraction.PostDao
import com.mejdoo.clean.data.source.local.abstraction.PostLocalDataSource
import com.mejdoo.clean.domain.model.Post
import io.reactivex.Single

class PostLocalDataSourceImpl(
    private val dao: PostDao
) : PostLocalDataSource {

    override fun allPosts(): Single<List<Post>> =
        dao.allPosts()
            .map { it.toDomainList() }

    override fun postById(postId: Int): Single<Post> =
        dao.postById(postId)
            .map { it.toDomain() }

    override fun insertPost(post: Post) {
        dao.insertPost(post.toData())
    }
}