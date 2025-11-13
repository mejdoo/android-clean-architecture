package com.mejdoo.clean.presentation.viewmodel

import com.mejdoo.clean.CoroutineTestRule
import com.mejdoo.clean.domain.model.Comment
import com.mejdoo.clean.domain.model.Post
import com.mejdoo.clean.domain.model.User
import com.mejdoo.clean.domain.usecase.CombinedPostUserComments
import com.mejdoo.clean.domain.usecase.PostDetailUseCase
import com.mejdoo.clean.presentation.mapper.toPostDetail
import com.mejdoo.clean.presentation.model.UiState
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class PostDetailViewModelTest {

    @get:Rule
    val coroutineRule = CoroutineTestRule()

    @Mock
    private lateinit var postDetailUseCase: PostDetailUseCase

    private lateinit var viewModel: PostDetailViewModel

    private val post = Post(1, 1, "Title", "Body")
    private val user = User(1, "Name", "email@test.com", "+49000000", "test.com")
    private val comments = listOf(Comment(1, 1, "Name", "email@test.com", "Comment body"))
    private val combined = CombinedPostUserComments(post, user, comments)
    private val postDetail = combined.toPostDetail() // Assuming this extension function exists

    @Test
    fun `postDetailState emits Success when data is loaded`() = coroutineRule.testScope.runTest {
        whenever(postDetailUseCase(1, 1)).thenReturn(flowOf(combined))

        viewModel = PostDetailViewModel(postDetailUseCase)
        viewModel.loadPostDetail(1, 1)

        // Wait for the first Success emission
        val state = viewModel.postDetailState.first { it is UiState.Success }

        assertTrue(state is UiState.Success)
        assertEquals(postDetail, (state as UiState.Success).data)
    }


    @Test
    fun `postDetailState emits Error when use case throws`() = coroutineRule.testScope.runTest {
        val exception = RuntimeException("Network error")
        whenever(postDetailUseCase(1, 1)).thenReturn(flow { throw exception })

        viewModel = PostDetailViewModel(postDetailUseCase)
        viewModel.loadPostDetail(1, 1)

        // Suspend until the first Error emission arrives
        val state = viewModel.postDetailState.first { it is UiState.Error }

        assertTrue(state is UiState.Error)
        assertEquals("Network error", (state as UiState.Error).message)
    }


    @Test
    fun `postDetailState initial value is Loading`() {
        viewModel = PostDetailViewModel(postDetailUseCase)
        val initial = viewModel.postDetailState.value
        assert(initial is UiState.Loading)
    }
}
