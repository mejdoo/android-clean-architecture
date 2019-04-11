package com.mejdoo.clean.presentation.model


data class PostDetail(

    val postId: Int,

    val userId: Int,

    val title: String,

    val body: String,

    val username: String,

    val nbComments: Int


)