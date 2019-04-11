package com.mejdoo.clean.data.repository

import com.mejdoo.clean.data.source.local.abstraction.PostLocalDataSource
import com.mejdoo.clean.data.source.remote.abstraction.PostRemoteDataSource
import com.mejdoo.clean.post1
import com.mejdoo.clean.post2
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class PostRepositoryImplTest {


    private lateinit var repository: PostRepositoryImpl

    @Mock
    private lateinit var mockRemoteDataSource: PostRemoteDataSource

    @Mock
    private lateinit var mockLocalDataSource: PostLocalDataSource

    private val posts = listOf(post1, post2)

    private val throwable = Throwable()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        repository = PostRepositoryImpl(mockRemoteDataSource, mockLocalDataSource)
    }

    @Test
    fun test_GetPostById_RemoteDataSource_Success() {

        val postId = 1

        `when`(mockRemoteDataSource.getPostById(postId)).thenReturn(Single.just(post1))


        val test = repository.getPostById(postId).test()

        verify(mockRemoteDataSource).getPostById(postId)
        test.assertValue(post1)
    }

    @Test
    fun test_GetPostById_RemoteDataSource_Failure_LocalDataSource_Success() {

        val postId = 1

        `when`(mockRemoteDataSource.getPostById(postId)).thenReturn(Single.error(throwable))
        `when`(mockLocalDataSource.getPostById(postId)).thenReturn(Single.just(post1))

        val test = repository.getPostById(postId).test()


        verify(mockRemoteDataSource).getPostById(postId)
        verify(mockLocalDataSource).getPostById(postId)

        test.assertValue(post1)
    }

    @Test
    fun test_GetPostById_RemoteDataSource_Failure_LocalDataSource_Failure() {

        val postId = 1

        `when`(mockRemoteDataSource.getPostById(postId)).thenReturn(Single.error(throwable))
        `when`(mockLocalDataSource.getPostById(postId)).thenReturn(Single.error(throwable))

        val test = repository.getPostById(postId).test()


        verify(mockRemoteDataSource).getPostById(postId)
        verify(mockLocalDataSource).getPostById(postId)

        test.assertError(throwable)
    }


    @Test
    fun test_GetAllPosts_RemoteDataSource_Success() {


        `when`(mockRemoteDataSource.getAllPosts()).thenReturn(Single.just(posts))


        val test = repository.getAllPosts().test()

        verify(mockRemoteDataSource).getAllPosts()
        test.assertValue(posts)
    }

    @Test
    fun test_GetAllPosts_RemoteDataSource_Failure_LocalDataSource_Success() {

        `when`(mockRemoteDataSource.getAllPosts()).thenReturn(Single.error(throwable))
        `when`(mockLocalDataSource.getAllPosts()).thenReturn(Single.just(posts))

        val test = repository.getAllPosts().test()


        verify(mockRemoteDataSource).getAllPosts()
        verify(mockLocalDataSource).getAllPosts()

        test.assertValue(posts)
    }

    @Test
    fun test_GetAllPosts_RemoteDataSource_Failure_LocalDataSource_Failure() {

        `when`(mockRemoteDataSource.getAllPosts()).thenReturn(Single.error(throwable))
        `when`(mockLocalDataSource.getAllPosts()).thenReturn(Single.error(throwable))

        val test = repository.getAllPosts().test()


        verify(mockRemoteDataSource).getAllPosts()
        verify(mockLocalDataSource).getAllPosts()

        test.assertError(throwable)
    }

}