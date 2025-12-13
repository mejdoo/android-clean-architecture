package com.mejdoo.clean.data.source.remote

import com.mejdoo.clean.data.mapper.toCommentList
import com.mejdoo.clean.data.model.CommentEntity
import com.mejdoo.clean.data.source.remote.abstraction.CleanApi
import com.mejdoo.clean.data.source.remote.implementation.CommentRemoteDataSourceImpl
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class CommentRemoteDataSourceImplTest {
    @Mock
    private lateinit var api: CleanApi

    private lateinit var dataSource: CommentRemoteDataSourceImpl

    @Before
    fun setUp() {
        dataSource = CommentRemoteDataSourceImpl(api)
    }

    // --------------------------------------------------------------------
    // commentsForPost()
    // --------------------------------------------------------------------

    @Test
    fun `commentsForPost returns mapped comments from api`() =
        runTest {
            // given
            val postId = 1
            val apiResponse =
                listOf(
                    CommentEntity(postId, 1, "Name 1", "email1@example.com", "Body 1"),
                    CommentEntity(postId, 2, "Name 2", "email2@example.com", "Body 2"),
                )
            val expectedComments = apiResponse.toCommentList()

            whenever(api.commentsForPost(postId)).thenReturn(apiResponse)

            // when
            val result = dataSource.commentsForPost(postId)

            // then
            assertEquals(expectedComments, result)
            verify(api).commentsForPost(postId)
        }

    @Test(expected = RuntimeException::class)
    fun `commentsForPost propagates api exception`() =
        runTest {
            val postId = 1
            whenever(api.commentsForPost(postId)).thenThrow(RuntimeException("Network error"))

            // when
            dataSource.commentsForPost(postId) // should throw
        }
}
