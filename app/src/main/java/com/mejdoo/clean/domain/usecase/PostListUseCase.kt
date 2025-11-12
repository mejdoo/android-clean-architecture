package com.mejdoo.clean.domain.usecase

import com.mejdoo.clean.domain.model.Post
import com.mejdoo.clean.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow

class PostListUseCase(
    private val postRepository: PostRepository,
) {
    operator fun invoke(): Flow<List<Post>> = postRepository.allPosts()
}
