package com.mejdoo.clean.data.source.local

import com.mejdoo.clean.commentEntity1
import com.mejdoo.clean.commentEntity2
import com.mejdoo.clean.data.mapper.CommentEntityMapper
import com.mejdoo.clean.data.source.local.abstraction.CommentDao
import com.mejdoo.clean.data.source.local.implementation.CommentLocalDataSourceImpl
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations


class CommentLocalDataSourceImplTest {

    @Mock
    private lateinit var mockDao: CommentDao

    private lateinit var dataSource: CommentLocalDataSourceImpl

    private val mapper = CommentEntityMapper()

    private val localList = listOf(commentEntity1, commentEntity2)

    private val throwable = Throwable()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        dataSource = CommentLocalDataSourceImpl(mockDao, mapper)
    }


    @Test
    fun test_GetCommentsByPostId_Success() {

        val postId = 1

        `when`(mockDao.getCommentsByPostId(postId)).thenReturn(Single.just(localList))


        val test = dataSource.getCommentsByPostId(postId).test()

        verify(mockDao).getCommentsByPostId(postId)
        test.assertValue(mapper.mapListToDomain(localList))
    }

    @Test
    fun test_GetCommentsByPostId_Failure() {

        val userId = 1

        `when`(mockDao.getCommentsByPostId(userId)).thenReturn(Single.error(throwable))

        val test = dataSource.getCommentsByPostId(userId).test()

        verify(mockDao).getCommentsByPostId(userId)
        test.assertError(throwable)
    }

}

