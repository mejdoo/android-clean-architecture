package com.mejdoo.clean.data.repository

import com.mejdoo.clean.data.source.local.abstraction.PostLocalDataSource
import com.mejdoo.clean.data.source.remote.abstraction.PostRemoteDataSource
import com.mejdoo.clean.post1
import com.mejdoo.clean.post2
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class PostRepositoryImplTest {

    private lateinit var closeable: AutoCloseable

    private lateinit var repository: PostRepositoryImpl

    @Mock
    private lateinit var mockRemoteDataSource: PostRemoteDataSource

    @Mock
    private lateinit var mockLocalDataSource: PostLocalDataSource

    private val posts = listOf(post1, post2)

    private val throwable = Throwable()

    @Before
    fun setUp() {
        closeable = MockitoAnnotations.openMocks(this)
        repository = PostRepositoryImpl(mockRemoteDataSource, mockLocalDataSource)
    }

    @After
    fun tearDown() {
        closeable.close()
    }

    @Test
    fun test_PostById_RemoteDataSource_Success() {

        val postId = 1

        `when`(mockRemoteDataSource.postById(postId)).thenReturn(Single.just(post1))


        val test = repository.postById(postId).test()

        verify(mockRemoteDataSource).postById(postId)
        test.assertValue(post1)
    }

    @Test
    fun test_PostById_RemoteDataSource_Failure_LocalDataSource_Success() {

        val postId = 1

        `when`(mockRemoteDataSource.postById(postId)).thenReturn(Single.error(throwable))
        `when`(mockLocalDataSource.postById(postId)).thenReturn(Single.just(post1))

        val test = repository.postById(postId).test()


        verify(mockRemoteDataSource).postById(postId)
        verify(mockLocalDataSource).postById(postId)

        test.assertValue(post1)
    }

    @Test
    fun test_PostById_RemoteDataSource_Failure_LocalDataSource_Failure() {

        val postId = 1

        `when`(mockRemoteDataSource.postById(postId)).thenReturn(Single.error(throwable))
        `when`(mockLocalDataSource.postById(postId)).thenReturn(Single.error(throwable))

        val test = repository.postById(postId).test()


        verify(mockRemoteDataSource).postById(postId)
        verify(mockLocalDataSource).postById(postId)

        test.assertError(throwable)
    }


    @Test
    fun test_AllPosts_RemoteDataSource_Success() {


        `when`(mockRemoteDataSource.allPosts()).thenReturn(Single.just(posts))


        val test = repository.allPosts().test()

        verify(mockRemoteDataSource).allPosts()
        test.assertValue(posts)
    }

    @Test
    fun test_AllPosts_RemoteDataSource_Failure_LocalDataSource_Success() {

        `when`(mockRemoteDataSource.allPosts()).thenReturn(Single.error(throwable))
        `when`(mockLocalDataSource.allPosts()).thenReturn(Single.just(posts))

        val test = repository.allPosts().test()


        verify(mockRemoteDataSource).allPosts()
        verify(mockLocalDataSource).allPosts()

        test.assertValue(posts)
    }

    @Test
    fun test_AllPosts_RemoteDataSource_Failure_LocalDataSource_Failure() {

        `when`(mockRemoteDataSource.allPosts()).thenReturn(Single.error(throwable))
        `when`(mockLocalDataSource.allPosts()).thenReturn(Single.error(throwable))

        val test = repository.allPosts().test()


        verify(mockRemoteDataSource).allPosts()
        verify(mockLocalDataSource).allPosts()

        test.assertError(throwable)
    }

}