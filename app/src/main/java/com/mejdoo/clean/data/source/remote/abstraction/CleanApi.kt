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
    fun getAllPosts(): Single<List<PostEntity>>

    @GET("/posts/{id}")
    fun getPostById(@Path("id") postId: Int): Single<PostEntity>

    @GET("/users/{id}")
    fun getUserById(@Path("id") userId: Int): Single<UserEntity>

    @GET("/comments")
    fun getCommentsByPostId(@Query("postId") postId: Int): Single<List<CommentEntity>>

}