package com.mejdoo.clean.domain.usecase


import com.mejdoo.clean.domain.repository.PostRepository
import com.mejdoo.clean.post1
import com.mejdoo.clean.post2
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations


class PostListUseCaseTest {

    private lateinit var useCase: PostListUseCase

    @Mock
    private lateinit var mockRepository: PostRepository

    private val posts = listOf(post1, post2)

    private val throwable = Throwable()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        useCase = PostListUseCase(mockRepository)
    }

    @Test
    fun test_PostList_Success() {


        Mockito.`when`(mockRepository.allPosts()).thenReturn(Single.just(posts))

        val test = useCase.postList().test()


        verify(mockRepository).allPosts()

        test.assertValue(posts)
    }

    @Test
    fun test_PostList_Failure() {

        Mockito.`when`(mockRepository.allPosts()).thenReturn(Single.error(throwable))

        val test = useCase.postList().test()

        verify(mockRepository).allPosts()

        test.assertError(throwable)
    }
}