package com.mejdoo.clean.data.repository

import com.mejdoo.clean.comment1
import com.mejdoo.clean.comment2
import com.mejdoo.clean.data.source.local.abstraction.CommentLocalDataSource
import com.mejdoo.clean.data.source.remote.abstraction.CommentRemoteDataSource
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class CommentRepositoryImplTest {

    private lateinit var closeable: AutoCloseable

    private lateinit var repository: CommentRepositoryImpl

    @Mock
    private lateinit var mockRemoteDataSource: CommentRemoteDataSource

    @Mock
    private lateinit var mockLocalDataSource: CommentLocalDataSource

    private val comments = listOf(comment1, comment2)

    private val throwable = Throwable()

    @Before
    fun setUp() {
        closeable = MockitoAnnotations.openMocks(this)
        repository = CommentRepositoryImpl(mockRemoteDataSource, mockLocalDataSource)
    }

    @After
    fun tearDown() {
        closeable.close()
    }

    @Test
    fun test_CommentsForPost_RemoteDataSource_Success() {

        val postId = 1

        `when`(mockRemoteDataSource.commentsForPost(postId)).thenReturn(Single.just(comments))


        val test = repository.commentsForPost(postId).test()

        verify(mockRemoteDataSource).commentsForPost(postId)
        test.assertValue(comments)
    }

    @Test
    fun test_CommentsForPost_RemoteDataSource_Failure_LocalDataSource_Success() {

        val postId = 1

        `when`(mockRemoteDataSource.commentsForPost(postId)).thenReturn(Single.error(throwable))
        `when`(mockLocalDataSource.commentsForPost(postId)).thenReturn(Single.just(comments))

        val test = repository.commentsForPost(postId).test()


        verify(mockRemoteDataSource).commentsForPost(postId)
        verify(mockLocalDataSource).commentsForPost(postId)

        test.assertValue(comments)
    }

    @Test
    fun test_CommentsForPost_RemoteDataSource_Failure_LocalDataSource_Failure() {

        val postId = 1

        `when`(mockRemoteDataSource.commentsForPost(postId)).thenReturn(Single.error(throwable))
        `when`(mockLocalDataSource.commentsForPost(postId)).thenReturn(Single.error(throwable))

        val test = repository.commentsForPost(postId).test()

        verify(mockRemoteDataSource).commentsForPost(postId)
        verify(mockLocalDataSource).commentsForPost(postId)

        test.assertError(throwable)
    }


}