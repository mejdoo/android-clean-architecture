package com.mejdoo.clean.data.source.local.implementation

import com.mejdoo.clean.data.mapper.toPost
import com.mejdoo.clean.data.mapper.toPostEntity
import com.mejdoo.clean.data.mapper.toPostList
import com.mejdoo.clean.data.source.local.abstraction.PostDao
import com.mejdoo.clean.data.source.local.abstraction.PostLocalDataSource
import com.mejdoo.clean.domain.model.Post
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PostLocalDataSourceImpl(private val dao: PostDao) : PostLocalDataSource {
    override fun allPosts(): Flow<List<Post>> = dao.allPosts().map { it.toPostList() }

    override fun postById(postId: Int): Flow<Post> = dao.postById(postId).map { it.toPost() }

    override suspend fun insertPost(post: Post) {
        dao.insertPost(post.toPostEntity())
    }
}
