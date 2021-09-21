package com.mejdoo.clean.domain.usecase

import com.mejdoo.clean.domain.model.Post
import com.mejdoo.clean.domain.repository.PostRepository
import io.reactivex.Single

class PostListUseCase constructor(private val postRepository: PostRepository) {
    fun getPostList(): Single<List<Post>> = postRepository.getAllPosts()
}

