package com.mejdoo.clean.presentation.viewmodel


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mejdoo.clean.domain.usecase.PostListUseCase
import com.mejdoo.clean.post1
import com.mejdoo.clean.post2
import com.mejdoo.clean.presentation.mapper.toPostItemList
import com.mejdoo.clean.presentation.model.PostItem
import com.mejdoo.clean.presentation.model.Resource
import com.mejdoo.clean.presentation.model.ResourceStatus
import io.reactivex.Single
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class PostListViewModelTest {

    private lateinit var closeable: AutoCloseable
    private lateinit var listViewModel: PostListViewModel

    @Mock
    private lateinit var mockUseCase: PostListUseCase

    private val posts = listOf(post1, post2)

    private val throwable = Throwable()

    @Rule
    @JvmField
    val rxSchedulersOverrideRule = RxSchedulersOverrideRule()

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        closeable = MockitoAnnotations.openMocks(this)
        listViewModel = PostListViewModel(mockUseCase)
    }

    @After
    fun tearDown() {
        closeable.close()
    }

    @Test
    fun test_PostList_Success() {

        `when`(mockUseCase.postList()).thenReturn(Single.just(posts))

        listViewModel.getPostList()


        verify(mockUseCase).postList()
        assertEquals(
            Resource<List<PostItem>>(
                ResourceStatus.SUCCESS,
                posts.toPostItemList(),
                null
            ),
            listViewModel.postItemsLiveData.value
        )
    }

    @Test
    fun test_PostList_Failure() {

        `when`(mockUseCase.postList()).thenReturn(Single.error(throwable))

        listViewModel.getPostList()

        verify(mockUseCase).postList()
        assertEquals(
            Resource<List<PostItem>>(
                ResourceStatus.ERROR,
                null,
                throwable.message
            ),
            listViewModel.postItemsLiveData.value
        )
    }

}