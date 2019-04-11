package com.mejdoo.clean.data.source.local.implementation

import com.mejdoo.clean.data.mapper.PostEntityMapper
import com.mejdoo.clean.data.source.local.abstraction.PostDao
import com.mejdoo.clean.data.source.local.abstraction.PostLocalDataSource
import com.mejdoo.clean.domain.model.Post
import io.reactivex.Single


class PostLocalDataSourceImpl constructor(
    private val dao: PostDao,
    private val mapper: PostEntityMapper
) : PostLocalDataSource {

    override fun getAllPosts(): Single<List<Post>> =
        dao.getAllPosts()
            .map { mapper.mapListToDomain(it) }

    override fun getPostById(postId: Int): Single<Post> =
        dao.getPostById(postId)
            .map { mapper.mapToDomain(it) }

    override fun insertPost(post: Post) {
        dao.insertPost(mapper.mapFromDomain(post))
    }

}