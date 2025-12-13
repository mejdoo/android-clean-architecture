package com.mejdoo.clean.data.source.local

import com.mejdoo.clean.data.mapper.toCommentEntity
import com.mejdoo.clean.data.mapper.toCommentList
import com.mejdoo.clean.data.model.CommentEntity
import com.mejdoo.clean.data.source.local.abstraction.CommentDao
import com.mejdoo.clean.data.source.local.implementation.CommentLocalDataSourceImpl
import com.mejdoo.clean.domain.model.Comment
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class CommentLocalDataSourceImplTest {
    @Mock
    private lateinit var dao: CommentDao

    private lateinit var dataSource: CommentLocalDataSourceImpl

    @Before
    fun setUp() {
        dataSource = CommentLocalDataSourceImpl(dao)
    }

    // --------------------------------------------------------------------
    // commentsForPost()
    // --------------------------------------------------------------------

    @Test
    fun `commentsForPost returns mapped comments from dao`() =
        runTest {
            // given
            val postId = 1
            val entities =
                listOf(
                    CommentEntity(1, postId, "Alice", "Nice post!", "Body 1"),
                    CommentEntity(2, postId, "Bob", "Interesting read.", "Body 2"),
                )
            val expectedComments = entities.toCommentList()

            whenever(dao.commentsForPost(postId)).thenReturn(flowOf(entities))

            // when
            val result = dataSource.commentsForPost(postId).first()

            // then
            assertEquals(expectedComments, result)
            verify(dao).commentsForPost(postId)
        }

    @Test(expected = RuntimeException::class)
    fun `commentsForPost propagates dao exception`() =
        runTest {
            // given
            val postId = 1
            whenever(dao.commentsForPost(postId)).thenThrow(RuntimeException("DB failure"))

            // when
            dataSource.commentsForPost(postId).first() // should throw
        }

    // --------------------------------------------------------------------
    // insertComment()
    // --------------------------------------------------------------------

    @Test
    fun `insertComment maps and inserts entity`() =
        runTest {
            // given
            val comment = Comment(1, 1, "Alice", "Cool post!", " Great body.")
            val expectedEntity = comment.toCommentEntity()

            // when
            dataSource.insertComment(comment)

            // then
            val captor = argumentCaptor<CommentEntity>()
            verify(dao).insertComment(captor.capture())
            assertEquals(expectedEntity, captor.firstValue)
        }

    @Test(expected = RuntimeException::class)
    fun `insertComment propagates dao exception`() =
        runTest {
            // given
            val comment = Comment(1, 1, "User", "Test comment", "Test body")
            whenever(dao.insertComment(any())).thenThrow(RuntimeException("DB insert failed"))

            // when
            dataSource.insertComment(comment) // should throw
        }
}
