package com.mejdoo.clean.domain.usecase

import com.mejdoo.clean.domain.model.Post
import com.mejdoo.clean.domain.repository.PostRepository
import io.reactivex.Single

class PostListUseCase(
    private val postRepository: PostRepository,
) {
    fun postList(): Single<List<Post>> = postRepository.allPosts()
}
