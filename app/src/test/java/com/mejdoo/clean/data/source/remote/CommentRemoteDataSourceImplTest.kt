package com.mejdoo.clean.data.source.remote

import com.mejdoo.clean.commentEntity1
import com.mejdoo.clean.commentEntity2
import com.mejdoo.clean.data.mapper.toCommentList
import com.mejdoo.clean.data.source.remote.abstraction.CleanApi
import com.mejdoo.clean.data.source.remote.implementation.CommentRemoteDataSourceImpl
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class CommentRemoteDataSourceImplTest {
    private lateinit var closeable: AutoCloseable

    @Mock
    private lateinit var mockApi: CleanApi

    private lateinit var dataSource: CommentRemoteDataSourceImpl

    private val remoteList = listOf(commentEntity1, commentEntity2)

    private val throwable = Throwable()

    @Before
    fun setUp() {
        closeable = MockitoAnnotations.openMocks(this)
        dataSource = CommentRemoteDataSourceImpl(mockApi)
    }

    @After
    fun tearDown() {
        closeable.close()
    }

    @Test
    fun test_CommentsForPost_Success() {
        val postId = 1

        `when`(mockApi.commentsForPost(postId)).thenReturn(Single.just(remoteList))

        val test = dataSource.commentsForPost(postId).test()

        verify(mockApi).commentsForPost(postId)
        test.assertValue(remoteList.toCommentList())
    }

    @Test
    fun test_CommentsForPost_Failure() {
        val userId = 1

        `when`(mockApi.commentsForPost(userId)).thenReturn(Single.error(throwable))

        val test = dataSource.commentsForPost(userId).test()

        verify(mockApi).commentsForPost(userId)
        test.assertError(throwable)
    }
}
