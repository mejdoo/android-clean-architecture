package com.mejdoo.clean.data.source.local

import com.mejdoo.clean.commentEntity1
import com.mejdoo.clean.commentEntity2
import com.mejdoo.clean.data.mapper.toCommentList
import com.mejdoo.clean.data.source.local.abstraction.CommentDao
import com.mejdoo.clean.data.source.local.implementation.CommentLocalDataSourceImpl
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class CommentLocalDataSourceImplTest {
    private lateinit var closeable: AutoCloseable

    @Mock
    private lateinit var mockDao: CommentDao

    private lateinit var dataSource: CommentLocalDataSourceImpl

    private val localList = listOf(commentEntity1, commentEntity2)

    private val throwable = Throwable()

    @Before
    fun setUp() {
        closeable = MockitoAnnotations.openMocks(this)
        dataSource = CommentLocalDataSourceImpl(mockDao)
    }

    @After
    fun tearDown() {
        closeable.close()
    }

    @Test
    fun test_CommentsForPost_Success() {
        val postId = 1

        `when`(mockDao.commentsForPost(postId)).thenReturn(Single.just(localList))

        val test = dataSource.commentsForPost(postId).test()

        verify(mockDao).commentsForPost(postId)
        test.assertValue(localList.toCommentList())
    }

    @Test
    fun test_CommentsForPost_Failure() {
        val userId = 1

        `when`(mockDao.commentsForPost(userId)).thenReturn(Single.error(throwable))

        val test = dataSource.commentsForPost(userId).test()

        verify(mockDao).commentsForPost(userId)
        test.assertError(throwable)
    }
}
