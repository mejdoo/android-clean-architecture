package com.mejdoo.clean.domain.usecase

import com.mejdoo.clean.domain.model.Post
import com.mejdoo.clean.domain.repository.PostRepository
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
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
class PostListUseCaseTest {
    @Mock
    private lateinit var postRepository: PostRepository

    private lateinit var useCase: PostListUseCase

    @Before
    fun setUp() {
        useCase = PostListUseCase(postRepository)
    }

    @Test
    fun `invoke returns posts from repository`() = runTest {
        // given
        val posts =
            listOf(
                Post(1, 1, "Title 1", "Body 1"),
                Post(2, 2, "Title 2", "Body 2")
            )
        whenever(postRepository.allPosts()).thenReturn(flowOf(posts))

        // when
        val result = useCase().first()

        // then
        assertEquals(posts, result)
        verify(postRepository).allPosts()
    }

    @Test
    fun `invoke emits empty list when repository returns empty`() = runTest {
        whenever(postRepository.allPosts()).thenReturn(flowOf(emptyList()))

        val result = useCase().first()

        assertTrue(result.isEmpty())
        verify(postRepository).allPosts()
    }
}
