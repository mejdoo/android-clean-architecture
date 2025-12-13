package com.mejdoo.clean.data.source.remote

import com.mejdoo.clean.data.mapper.toPost
import com.mejdoo.clean.data.mapper.toPostList
import com.mejdoo.clean.data.model.PostEntity
import com.mejdoo.clean.data.source.remote.abstraction.CleanApi
import com.mejdoo.clean.data.source.remote.implementation.PostRemoteDataSourceImpl
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
class PostRemoteDataSourceImplTest {
    @Mock
    private lateinit var api: CleanApi

    private lateinit var dataSource: PostRemoteDataSourceImpl

    @Before
    fun setUp() {
        dataSource = PostRemoteDataSourceImpl(api)
    }

    // --------------------------------------------------------------------
    // allPosts()
    // --------------------------------------------------------------------

    @Test
    fun `allPosts returns mapped posts from api`() =
        runTest {
            // given
            val apiResponse =
                listOf(
                    PostEntity(1, 1, "Title 1", "Body 1"),
                    PostEntity(2, 2, "Title 2", "Body 2"),
                )
            val expectedPosts = apiResponse.toPostList()

            whenever(api.allPosts()).thenReturn(apiResponse)

            // when
            val result = dataSource.allPosts()

            // then
            assertEquals(expectedPosts, result)
            verify(api).allPosts()
        }

    @Test(expected = RuntimeException::class)
    fun `allPosts propagates api exception`() =
        runTest {
            whenever(api.allPosts()).thenThrow(RuntimeException("Network error"))

            // when
            dataSource.allPosts() // should throw
        }
    // --------------------------------------------------------------------
    // postById()
    // --------------------------------------------------------------------

    @Test
    fun `postById returns mapped post from api`() =
        runTest {
            val postId = 1
            val apiResponse = PostEntity(postId, 1, "Title 1", "Body 1")
            val expectedPost = apiResponse.toPost()

            whenever(api.postById(postId)).thenReturn(apiResponse)

            val result = dataSource.postById(postId)

            assertEquals(expectedPost, result)
            verify(api).postById(postId)
        }

    @Test(expected = RuntimeException::class)
    fun `postById propagates api exception`() =
        runTest {
            val postId = 1
            whenever(api.postById(postId)).thenThrow(RuntimeException("Network error"))

            dataSource.postById(postId) // should throw
        }
}
