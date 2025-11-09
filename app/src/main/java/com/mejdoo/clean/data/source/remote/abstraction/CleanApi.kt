package com.mejdoo.clean.data.source.remote.abstraction


import com.mejdoo.clean.data.model.CommentEntity
import com.mejdoo.clean.data.model.PostEntity
import com.mejdoo.clean.data.model.UserEntity
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CleanApi {

    @GET("/posts")
    fun allPosts(): Single<List<PostEntity>>

    @GET("/posts/{id}")
    fun postById(@Path("id") postId: Int): Single<PostEntity>

    @GET("/users/{id}")
    fun userById(@Path("id") userId: Int): Single<UserEntity>

    @GET("/comments")
    fun commentsForPost(@Query("postId") postId: Int): Single<List<CommentEntity>>
}