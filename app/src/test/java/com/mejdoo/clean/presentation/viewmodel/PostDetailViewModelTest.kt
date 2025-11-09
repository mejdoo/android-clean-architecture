package com.mejdoo.clean.presentation.viewmodel


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mejdoo.clean.comment1
import com.mejdoo.clean.comment2
import com.mejdoo.clean.domain.usecase.CombinedPostUserComments
import com.mejdoo.clean.domain.usecase.PostDetailUseCase
import com.mejdoo.clean.post1
import com.mejdoo.clean.presentation.mapper.toPostDetail
import com.mejdoo.clean.presentation.model.PostDetail
import com.mejdoo.clean.presentation.model.Resource
import com.mejdoo.clean.presentation.model.ResourceStatus
import com.mejdoo.clean.user1
import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class PostDetailViewModelTest {

    private lateinit var detailViewModel: PostDetailViewModel

    @Mock
    private lateinit var mockUseCase: PostDetailUseCase


    private val combinedPostUserComments =
        CombinedPostUserComments(
            post1,
            user1, listOf(comment1, comment2)
        )

    private val throwable = Throwable()

    @Rule
    @JvmField
    val rxSchedulersOverrideRule = RxSchedulersOverrideRule()

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        detailViewModel = PostDetailViewModel(mockUseCase)
    }

    @Test
    fun test_PostDetails_Success() {

        val postId = 1

        val userId = 1

        `when`(mockUseCase.postDetails(postId, userId)).thenReturn(
            Single.just(
                combinedPostUserComments
            )
        )

        detailViewModel.getPostDetail(postId, userId)


        verify(mockUseCase).postDetails(postId, userId)
        assertEquals(
            Resource<PostDetail>(
                ResourceStatus.SUCCESS,
                combinedPostUserComments.toPostDetail(),
                null
            ),
            detailViewModel.postDetailLiveData.value
        )
    }

    @Test
    fun test_PostDetails_Failure() {

        val postId = 1

        val userId = 1

        `when`(mockUseCase.postDetails(postId, userId)).thenReturn(Single.error(throwable))

        detailViewModel.getPostDetail(postId, userId)

        verify(mockUseCase).postDetails(postId, userId)

        assertEquals(
            Resource<PostDetail>(
                ResourceStatus.ERROR,
                null,
                throwable.message
            ),
            detailViewModel.postDetailLiveData.value
        )
    }

}