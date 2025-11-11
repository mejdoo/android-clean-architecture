package com.mejdoo.clean.data.source.remote

import com.mejdoo.clean.data.mapper.toPost
import com.mejdoo.clean.data.mapper.toPostList
import com.mejdoo.clean.data.source.remote.abstraction.CleanApi
import com.mejdoo.clean.data.source.remote.implementation.PostRemoteDataSourceImpl
import com.mejdoo.clean.postEntity1
import com.mejdoo.clean.postEntity2
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class PostRemoteDataSourceImplTest {
    private lateinit var closeable: AutoCloseable

    @Mock
    private lateinit var mockApi: CleanApi

    private lateinit var dataSource: PostRemoteDataSourceImpl

    private val remoteList = listOf(postEntity1, postEntity2)

    private val throwable = Throwable()

    @Before
    fun setUp() {
        closeable = MockitoAnnotations.openMocks(this)
        dataSource = PostRemoteDataSourceImpl(mockApi)
    }

    @After
    fun tearDown() {
        closeable.close()
    }

    @Test
    fun test_AllPosts_Success() {
        `when`(mockApi.allPosts()).thenReturn(Single.just(remoteList))

        val test = dataSource.allPosts().test()

        verify(mockApi).allPosts()
        test.assertValue(remoteList.toPostList())
    }

    @Test
    fun test_AllPosts_Failure() {
        `when`(mockApi.allPosts()).thenReturn(Single.error(throwable))

        val test = dataSource.allPosts().test()

        verify(mockApi).allPosts()
        test.assertError(throwable)
    }

    @Test
    fun test_PostById_Success() {
        val postId = 1

        `when`(mockApi.postById(postId)).thenReturn(Single.just(postEntity1))

        val test = dataSource.postById(postId).test()

        verify(mockApi).postById(postId)
        test.assertValue(postEntity1.toPost())
    }

    @Test
    fun test_PostById_Failure() {
        val postId = 1

        `when`(mockApi.postById(postId)).thenReturn(Single.error(throwable))

        val test = dataSource.postById(postId).test()

        verify(mockApi).postById(postId)
        test.assertError(throwable)
    }
}
