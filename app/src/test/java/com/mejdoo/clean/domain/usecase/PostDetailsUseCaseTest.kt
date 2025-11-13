package com.mejdoo.clean.domain.usecase

import com.mejdoo.clean.domain.model.Comment
import com.mejdoo.clean.domain.model.Post
import com.mejdoo.clean.domain.model.User
import com.mejdoo.clean.domain.repository.CommentRepository
import com.mejdoo.clean.domain.repository.PostRepository
import com.mejdoo.clean.domain.repository.UserRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class PostDetailUseCaseTest {

    @Mock
    private lateinit var postRepository: PostRepository

    @Mock
    private lateinit var userRepository: UserRepository

    @Mock
    private lateinit var commentRepository: CommentRepository

    private lateinit var useCase: PostDetailUseCase

    @Before
    fun setUp() {
        useCase = PostDetailUseCase(postRepository, userRepository, commentRepository)
    }

    @Test
    fun `invoke combines post, user and comments`() = runTest {
        val postId = 1
        val userId = 10

        val post = Post(postId, userId, "Title", "Body")
        val user = User(userId, "User Name", "email@example.com", "+1234567890", "website.com")
        val comments = listOf(
            Comment(1, postId, "Commenter", "email1@example.com", "Body 1"),
            Comment(2, postId, "Commenter 2", "email2@example.com", "Body 2")
        )

        whenever(postRepository.postById(postId)).thenReturn(flowOf(post))
        whenever(userRepository.userById(userId)).thenReturn(flowOf(user))
        whenever(commentRepository.commentsForPost(postId)).thenReturn(flowOf(comments))

        val result = useCase(postId, userId).first()

        assertEquals(post, result.post)
        assertEquals(user, result.user)
        assertEquals(comments, result.comments)

        verify(postRepository).postById(postId)
        verify(userRepository).userById(userId)
        verify(commentRepository).commentsForPost(postId)
    }

    @Test
    fun `invoke emits empty comments when repository returns empty`() = runTest {
        val postId = 1
        val userId = 10

        val post = Post(postId, userId, "Title", "Body")
        val user = User(userId, "User Name", "email@example.com", "+1234567890", "website.com")
        val emptyComments = emptyList<Comment>()

        whenever(postRepository.postById(postId)).thenReturn(flowOf(post))
        whenever(userRepository.userById(userId)).thenReturn(flowOf(user))
        whenever(commentRepository.commentsForPost(postId)).thenReturn(flowOf(emptyComments))

        val result = useCase(postId, userId).first()

        assertEquals(post, result.post)
        assertEquals(user, result.user)
        assertEquals(emptyComments, result.comments)
    }
}
