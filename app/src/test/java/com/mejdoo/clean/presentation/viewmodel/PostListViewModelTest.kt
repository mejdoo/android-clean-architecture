package com.mejdoo.clean.presentation.viewmodel

import com.mejdoo.clean.CoroutineTestRule
import com.mejdoo.clean.domain.model.Post
import com.mejdoo.clean.domain.usecase.PostListUseCase
import com.mejdoo.clean.presentation.mapper.toPostItemList
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
class PostListViewModelTest {

    @get:Rule
    val coroutineRule = CoroutineTestRule()

    @Mock
    private lateinit var postListUseCase: PostListUseCase

    private lateinit var viewModel: PostListViewModel

    @Test
    fun `postItemsState emits Success when posts are loaded`() = coroutineRule.testScope.runTest {
        val posts = listOf(Post(1, 1, "Title", "Body"))
        whenever(postListUseCase()).thenReturn(flowOf(posts))

        viewModel = PostListViewModel(postListUseCase)

        // Wait for the first Success emission
        val state = viewModel.postItemsState.first { it is UiState.Success }

        assertTrue(state is UiState.Success)
        assertEquals(posts.toPostItemList(), (state as UiState.Success).data)
    }


    @Test
    fun `postItemsState emits Error when use case throws`() = coroutineRule.testScope.runTest {
        val exception = RuntimeException("Network error")
        whenever(postListUseCase()).thenReturn(flow { throw exception })

        viewModel = PostListViewModel(postListUseCase)

        // Wait for the first Error emission
        val state = viewModel.postItemsState.first { it is UiState.Error }

        assertTrue(state is UiState.Error)
        assertEquals("Network error", (state as UiState.Error).message)
    }


    @Test
    fun `postItemsState initial value is Loading`() {
        whenever(postListUseCase()).thenReturn(flowOf(emptyList()))

        viewModel = PostListViewModel(postListUseCase)

        val initial = viewModel.postItemsState.value
        assert(initial is UiState.Loading)
    }
}
