package com.mejdoo.clean.data.source.remote.abstraction

import com.mejdoo.clean.data.model.CommentEntity
import com.mejdoo.clean.data.model.PostEntity
import com.mejdoo.clean.data.model.UserEntity
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CleanApi {
    @GET("/posts")
    suspend fun allPosts(): List<PostEntity>

    @GET("/posts/{id}")
    suspend fun postById(
        @Path("id") postId: Int,
    ): PostEntity

    @GET("/users/{id}")
    suspend fun userById(
        @Path("id") userId: Int,
    ): UserEntity

    @GET("/comments")
    suspend fun commentsForPost(
        @Query("postId") postId: Int,
    ): List<CommentEntity>
}
