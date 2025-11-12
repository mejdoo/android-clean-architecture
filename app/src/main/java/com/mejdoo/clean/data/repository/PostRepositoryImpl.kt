package com.mejdoo.clean.data.repository

import com.mejdoo.clean.data.source.local.abstraction.PostLocalDataSource
import com.mejdoo.clean.data.source.remote.abstraction.PostRemoteDataSource
import com.mejdoo.clean.domain.model.Post
import com.mejdoo.clean.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onStart

class PostRepositoryImpl(
    private val remoteDataSource: PostRemoteDataSource,
    private val localDataSource: PostLocalDataSource,
) : PostRepository {
    override fun allPosts(): Flow<List<Post>> =
        localDataSource.allPosts().onStart {
            try {
                val posts = remoteDataSource.allPosts()
                posts.forEach { post -> localDataSource.insertPost(post) }
            } catch (_: Exception) {
                // ignore remote errors, local flow will emit cached data
            }
        }

    override fun postById(postId: Int): Flow<Post> =
        localDataSource.postById(postId).onStart {
            try {
                val post = remoteDataSource.postById(postId)
                localDataSource.insertPost(post)
            } catch (_: Exception) {
                // ignore
            }
        }
}
