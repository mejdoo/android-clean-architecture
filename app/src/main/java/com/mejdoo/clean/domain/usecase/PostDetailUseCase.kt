package com.mejdoo.clean.domain.usecase

import com.mejdoo.clean.domain.model.Comment
import com.mejdoo.clean.domain.model.Post
import com.mejdoo.clean.domain.model.User
import com.mejdoo.clean.domain.repository.CommentRepository
import com.mejdoo.clean.domain.repository.PostRepository
import com.mejdoo.clean.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class PostDetailUseCase(
    private val postRepository: PostRepository,
    private val userRepository: UserRepository,
    private val commentRepository: CommentRepository,
) {
    operator fun invoke(
        postId: Int,
        userId: Int,
    ): Flow<CombinedPostUserComments> =
        combine(
            postRepository.postById(postId),
            userRepository.userById(userId),
            commentRepository.commentsForPost(postId),
        ) { post, user, comments ->
            CombinedPostUserComments(post, user, comments)
        }
}

data class CombinedPostUserComments(
    val post: Post,
    val user: User,
    val comments: List<Comment>,
)
