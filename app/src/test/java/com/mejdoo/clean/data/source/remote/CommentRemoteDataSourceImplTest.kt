package com.mejdoo.clean.data.source.remote

import com.mejdoo.clean.commentEntity1
import com.mejdoo.clean.commentEntity2
import com.mejdoo.clean.data.mapper.CommentEntityMapper
import com.mejdoo.clean.data.source.remote.abstraction.CleanApi
import com.mejdoo.clean.data.source.remote.implementation.CommentRemoteDataSourceImpl
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations


class CommentRemoteDataSourceImplTest {


    @Mock
    private lateinit var mockApi: CleanApi

    private lateinit var dataSource: CommentRemoteDataSourceImpl

    private val mapper = CommentEntityMapper()

    private val remoteList = listOf(commentEntity1, commentEntity2)

    private val throwable = Throwable()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        dataSource = CommentRemoteDataSourceImpl(mockApi, mapper)
    }


    @Test
    fun test_GetCommentsByPostId_Success() {

        val postId = 1

        `when`(mockApi.getCommentsByPostId(postId)).thenReturn(Single.just(remoteList))


        val test = dataSource.getCommentsByPostId(postId).test()

        verify(mockApi).getCommentsByPostId(postId)
        test.assertValue(mapper.mapListToDomain(remoteList))
    }

    @Test
    fun test_GetCommentsByPostId_Failure() {

        val userId = 1

        `when`(mockApi.getCommentsByPostId(userId)).thenReturn(Single.error(throwable))

        val test = dataSource.getCommentsByPostId(userId).test()

        verify(mockApi).getCommentsByPostId(userId)
        test.assertError(throwable)
    }


}

