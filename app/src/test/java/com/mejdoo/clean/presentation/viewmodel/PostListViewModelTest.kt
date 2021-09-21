package com.mejdoo.clean.presentation.viewmodel


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mejdoo.clean.domain.usecase.PostListUseCase
import com.mejdoo.clean.post1
import com.mejdoo.clean.post2
import com.mejdoo.clean.presentation.mapper.PostItemMapper
import com.mejdoo.clean.presentation.model.PostItem
import com.mejdoo.clean.presentation.model.Resource
import com.mejdoo.clean.presentation.model.ResourceStatus
import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class PostListViewModelTest {

    private lateinit var listViewModel: PostListViewModel

    @Mock
    private lateinit var mockUseCase: PostListUseCase

    private val posts = listOf(post1, post2)

    private val throwable = Throwable()

    private val mapper = PostItemMapper()

    @Rule
    @JvmField
    val rxSchedulersOverrideRule = RxSchedulersOverrideRule()

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        listViewModel = PostListViewModel(mockUseCase, mapper)
    }

    @Test
    fun test_GetPostList_Success() {

        `when`(mockUseCase.getPostList()).thenReturn(Single.just(posts))

        listViewModel.getPostList()


        verify(mockUseCase).getPostList()
        assertEquals(
                Resource<List<PostItem>>(
                        ResourceStatus.SUCCESS,
                        mapper.mapListFromDomain(posts),
                        null
                ),
                listViewModel.postItemsLiveData.value
        )
    }

    @Test
    fun test_GetPostList_Failure() {

        `when`(mockUseCase.getPostList()).thenReturn(Single.error(throwable))

        listViewModel.getPostList()

        verify(mockUseCase).getPostList()
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