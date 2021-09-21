package com.mejdoo.clean.domain.usecase

import com.mejdoo.clean.domain.model.Comment
import com.mejdoo.clean.domain.model.Post
import com.mejdoo.clean.domain.model.User
import com.mejdoo.clean.domain.repository.CommentRepository
import com.mejdoo.clean.domain.repository.PostRepository
import com.mejdoo.clean.domain.repository.UserRepository
import io.reactivex.Single
import io.reactivex.functions.Function3

class PostDetailUseCase constructor(
        private val postRepository: PostRepository,
        private val userRepository: UserRepository,
        private val commentRepository: CommentRepository
) {

    fun getPostDetails(postId: Int, userId: Int): Single<CombinedPostUserComments> =
            Single.zip(postRepository.getPostById(postId),
                    userRepository.getUserById(userId),
                    commentRepository.getCommentsByPostId(postId),
                    Function3 { post, user, comments ->
                        CombinedPostUserComments(
                                post,
                                user,
                                comments
                        )
                    })

}


data class CombinedPostUserComments(val post: Post, val user: User, val comments: List<Comment>)
