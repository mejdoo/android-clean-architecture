package com.mejdoo.clean.data.repository

import com.mejdoo.clean.data.source.local.abstraction.CommentLocalDataSource
import com.mejdoo.clean.data.source.remote.abstraction.CommentRemoteDataSource
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
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.never
import org.mockito.kotlin.times
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class CommentRepositoryImplTest {
    @Mock
    private lateinit var remoteDataSource: CommentRemoteDataSource

    @Mock
    private lateinit var localDataSource: CommentLocalDataSource
    private lateinit var repository: CommentRepositoryImpl

    @Before
    fun setUp() {
        repository = CommentRepositoryImpl(remoteDataSource, localDataSource)
    }

    @Test
    fun `returns comments from local data source`() =
        runTest {
            val localComments = listOf(Comment(1, 1, "Local comment", "local@email.com", "Local Body"))
            whenever(localDataSource.commentsForPost(1)).thenReturn(flowOf(localComments))
            whenever(remoteDataSource.commentsForPost(1)).thenReturn(emptyList())

            val result = repository.commentsForPost(1).first()

            assertEquals(result, localComments)
            verify(localDataSource).commentsForPost(1)
        }

    @Test
    fun `fetches from remote and inserts into local on start`() =
        runTest {
            val postId = 1
            val remoteComments =
                listOf(
                    Comment(1, postId, "Remote 1", "remote1@email.com", "Remote Body 1"),
                    Comment(2, postId, "Remote 2", "remote2@email.com", "Remote Body 2"),
                )

            whenever(localDataSource.commentsForPost(postId)).thenReturn(flowOf(emptyList()))
            whenever(remoteDataSource.commentsForPost(postId)).thenReturn(remoteComments)

            repository.commentsForPost(postId).first()

            verify(remoteDataSource).commentsForPost(postId)

            val captor = argumentCaptor<Comment>()
            verify(localDataSource, times(remoteComments.size)).insertComment(captor.capture())
            assertEquals(captor.allValues, remoteComments)
        }

    @Test
    fun `ignores remote exception and still emits local data`() =
        runTest {
            val postId = 1
            val localComments = listOf(Comment(1, postId, "Local", "local@email.com", "Local Body"))
            whenever(localDataSource.commentsForPost(postId)).thenReturn(flowOf(localComments))
            whenever(remoteDataSource.commentsForPost(postId)).thenThrow(RuntimeException("Network error"))

            val result = repository.commentsForPost(postId).first()

            assertEquals(result, localComments)
            verify(localDataSource, never()).insertComment(any())
        }
}
