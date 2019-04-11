package com.mejdoo.clean.data.repository

import com.mejdoo.clean.comment1
import com.mejdoo.clean.comment2
import com.mejdoo.clean.data.source.local.abstraction.CommentLocalDataSource
import com.mejdoo.clean.data.source.remote.abstraction.CommentRemoteDataSource
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class CommentRepositoryImplTest {


    private lateinit var repository: CommentRepositoryImpl

    @Mock
    private lateinit var mockRemoteDataSource: CommentRemoteDataSource

    @Mock
    private lateinit var mockLocalDataSource: CommentLocalDataSource

    private val comments = listOf(comment1, comment2)

    private val throwable = Throwable()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        repository = CommentRepositoryImpl(mockRemoteDataSource, mockLocalDataSource)
    }

    @Test
    fun test_GetCommentsByPostId_RemoteDataSource_Success() {

        val postId = 1

        `when`(mockRemoteDataSource.getCommentsByPostId(postId)).thenReturn(Single.just(comments))


        val test = repository.getCommentsByPostId(postId).test()

        verify(mockRemoteDataSource).getCommentsByPostId(postId)
        test.assertValue(comments)
    }

    @Test
    fun test_GetCommentsByPostId_RemoteDataSource_Failure_LocalDataSource_Success() {

        val postId = 1

        `when`(mockRemoteDataSource.getCommentsByPostId(postId)).thenReturn(Single.error(throwable))
        `when`(mockLocalDataSource.getCommentsByPostId(postId)).thenReturn(Single.just(comments))

        val test = repository.getCommentsByPostId(postId).test()


        verify(mockRemoteDataSource).getCommentsByPostId(postId)
        verify(mockLocalDataSource).getCommentsByPostId(postId)

        test.assertValue(comments)
    }

    @Test
    fun test_GetCommentsByPostId_RemoteDataSource_Failure_LocalDataSource_Failure() {

        val postId = 1

        `when`(mockRemoteDataSource.getCommentsByPostId(postId)).thenReturn(Single.error(throwable))
        `when`(mockLocalDataSource.getCommentsByPostId(postId)).thenReturn(Single.error(throwable))

        val test = repository.getCommentsByPostId(postId).test()


        verify(mockRemoteDataSource).getCommentsByPostId(postId)
        verify(mockLocalDataSource).getCommentsByPostId(postId)

        test.assertError(throwable)
    }


}