package com.mejdoo.clean.data.repository

import com.mejdoo.clean.data.source.local.abstraction.PostLocalDataSource
import com.mejdoo.clean.data.source.remote.abstraction.PostRemoteDataSource
import com.mejdoo.clean.domain.model.Post
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
import org.mockito.kotlin.never
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class PostRepositoryImplTest {
    @Mock
    private lateinit var remoteDataSource: PostRemoteDataSource

    @Mock
    private lateinit var localDataSource: PostLocalDataSource
    private lateinit var repository: PostRepositoryImpl

    @Before
    fun setUp() {
        repository = PostRepositoryImpl(remoteDataSource, localDataSource)
    }

    // --------------------------------------------------------------------
    // allPosts() tests
    // --------------------------------------------------------------------

    @Test
    fun `allPosts returns data from local source`() =
        runTest {
            val localPosts = listOf(Post(1, 1, "Local Title", "Local Body"))
            whenever(localDataSource.allPosts()).thenReturn(flowOf(localPosts))
            whenever(remoteDataSource.allPosts()).thenReturn(emptyList())

            val result = repository.allPosts().first()

            assertEquals(result, localPosts)

            verify(localDataSource).allPosts()
            verify(remoteDataSource).allPosts()
        }

    @Test
    fun `allPosts fetches from remote and inserts into local`() =
        runTest {
            val remotePosts =
                listOf(
                    Post(1, 1, "Remote 1", "Remote Body 1"),
                    Post(2, 2, "Remote 2", "Remote Body 2"),
                )
            whenever(localDataSource.allPosts()).thenReturn(flowOf(emptyList()))
            whenever(remoteDataSource.allPosts()).thenReturn(remotePosts)

            repository.allPosts().first()

            verify(remoteDataSource).allPosts()

            val captor = argumentCaptor<Post>()
            verify(localDataSource, times(remotePosts.size)).insertPost(captor.capture())
            assertEquals(captor.allValues, remotePosts)
        }

    @Test
    fun `allPosts ignores remote exceptions and still emits local`() =
        runTest {
            val localPosts = listOf(Post(1, 1, "Cached", "Cached Body"))
            whenever(localDataSource.allPosts()).thenReturn(flowOf(localPosts))
            whenever(remoteDataSource.allPosts()).thenThrow(RuntimeException("Network error"))

            val result = repository.allPosts().first()

            assertEquals(result, localPosts)
            verify(localDataSource, never()).insertPost(any())
        }

    // --------------------------------------------------------------------
    // postById() tests
    // --------------------------------------------------------------------

    @Test
    fun `postById returns local data`() =
        runTest {
            val localPost = Post(1, 1, "Local post", "Local Body")
            whenever(localDataSource.postById(1)).thenReturn(flowOf(localPost))
            whenever(remoteDataSource.postById(1)).thenReturn(Post(1, 1, "Remote post", "Remote Body"))

            val result = repository.postById(1).first()

            assertEquals(result, localPost)
            verify(localDataSource).postById(1)
            verify(remoteDataSource).postById(1)
        }

    @Test
    fun `postById fetches remote post and inserts into local`() =
        runTest {
            val postId = 1
            val remotePost = Post(postId, 1, "Remote post", "Remote Body")
            val cachedPost = Post(postId, 1, "Old cached post", "Old Body")

            whenever(localDataSource.postById(postId)).thenReturn(flowOf(cachedPost))
            whenever(remoteDataSource.postById(postId)).thenReturn(remotePost)

            repository.postById(postId).first()

            verify(remoteDataSource).postById(postId)

            val captor = argumentCaptor<Post>()
            verify(localDataSource).insertPost(captor.capture())

            val insertedPost = captor.firstValue
            assertEquals(insertedPost, remotePost)
        }

    @Test
    fun `postById ignores remote exception and still emits cached post`() =
        runTest {
            val postId = 1
            val cachedPost = Post(postId, 1, "Cached post", "Cached Body")

            whenever(localDataSource.postById(postId)).thenReturn(flowOf(cachedPost))
            whenever(remoteDataSource.postById(postId)).thenThrow(RuntimeException("Network error"))

            val result = repository.postById(postId).first()

            assertEquals(result, cachedPost)
            verify(localDataSource, never()).insertPost(any())
        }
}
