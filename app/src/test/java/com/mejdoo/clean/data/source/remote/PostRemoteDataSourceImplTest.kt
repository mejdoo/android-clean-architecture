package com.mejdoo.clean.data.source.remote

import com.mejdoo.clean.data.mapper.PostEntityMapper
import com.mejdoo.clean.data.source.remote.abstraction.CleanApi
import com.mejdoo.clean.data.source.remote.implementation.PostRemoteDataSourceImpl
import com.mejdoo.clean.postEntity1
import com.mejdoo.clean.postEntity2
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations


class PostRemoteDataSourceImplTest {


    @Mock
    private lateinit var mockApi: CleanApi

    private lateinit var dataSource: PostRemoteDataSourceImpl

    private val mapper = PostEntityMapper()

    private val remoteList = listOf(postEntity1, postEntity2)

    private val throwable = Throwable()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        dataSource = PostRemoteDataSourceImpl(mockApi, mapper)
    }

    @Test
    fun test_GetAllPosts_Success() {

        `when`(mockApi.getAllPosts()).thenReturn(Single.just(remoteList))


        val test = dataSource.getAllPosts().test()

        verify(mockApi).getAllPosts()
        test.assertValue(mapper.mapListToDomain(remoteList))
    }

    @Test
    fun test_GetAllPosts_Failure() {

        `when`(mockApi.getAllPosts()).thenReturn(Single.error(throwable))

        val test = dataSource.getAllPosts().test()

        verify(mockApi).getAllPosts()
        test.assertError(throwable)
    }


    @Test
    fun test_GetPostById_Success() {

        val postId = 1

        `when`(mockApi.getPostById(postId)).thenReturn(Single.just(postEntity1))


        val test = dataSource.getPostById(postId).test()

        verify(mockApi).getPostById(postId)
        test.assertValue(mapper.mapToDomain(postEntity1))
    }

    @Test
    fun test_GetPostById_Failure() {

        val postId = 1

        `when`(mockApi.getPostById(postId)).thenReturn(Single.error(throwable))

        val test = dataSource.getPostById(postId).test()

        verify(mockApi).getPostById(postId)
        test.assertError(throwable)
    }

}

