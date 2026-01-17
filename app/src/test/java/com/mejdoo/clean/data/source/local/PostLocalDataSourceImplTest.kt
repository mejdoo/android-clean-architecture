package com.mejdoo.clean.data.source.local

import com.mejdoo.clean.data.mapper.toPost
import com.mejdoo.clean.data.mapper.toPostEntity
import com.mejdoo.clean.data.mapper.toPostList
import com.mejdoo.clean.data.model.PostEntity
import com.mejdoo.clean.data.source.local.abstraction.PostDao
import com.mejdoo.clean.data.source.local.implementation.PostLocalDataSourceImpl
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
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class PostLocalDataSourceImplTest {
    @Mock
    private lateinit var dao: PostDao

    private lateinit var dataSource: PostLocalDataSourceImpl

    @Before
    fun setUp() {
        dataSource = PostLocalDataSourceImpl(dao)
    }

    // --------------------------------------------------------------------
    // allPosts()
    // --------------------------------------------------------------------

    @Test
    fun `allPosts returns mapped posts from dao`() = runTest {
        // given
        val entities =
            listOf(
                PostEntity(1, 1, "Title 1", "Body 1"),
                PostEntity(2, 2, "Title 2", "Body 2")
            )
        val expectedPosts = entities.toPostList()

        whenever(dao.allPosts()).thenReturn(flowOf(entities))

        // when
        val result = dataSource.allPosts().first()

        // then
        assertEquals(expectedPosts, result)
        verify(dao).allPosts()
    }

    @Test(expected = RuntimeException::class)
    fun `allPosts propagates dao exception`() = runTest {
        whenever(dao.allPosts()).thenThrow(RuntimeException("DB failure"))
        dataSource.allPosts().first() // should throw
    }

    // --------------------------------------------------------------------
    // postById()
    // --------------------------------------------------------------------

    @Test
    fun `postById returns mapped post from dao`() = runTest {
        // given
        val postId = 1
        val entity = PostEntity(postId, 1, "Title", "Body")
        val expectedPost = entity.toPost()

        whenever(dao.postById(postId)).thenReturn(flowOf(entity))

        // when
        val result = dataSource.postById(postId).first()

        // then
        assertEquals(expectedPost, result)
        verify(dao).postById(postId)
    }

    @Test(expected = RuntimeException::class)
    fun `postById propagates dao exception`() = runTest {
        val postId = 1
        whenever(dao.postById(postId)).thenThrow(RuntimeException("DB read error"))
        dataSource.postById(postId).first() // should throw
    }

    // --------------------------------------------------------------------
    // insertPost()
    // --------------------------------------------------------------------

    @Test
    fun `insertPost maps and inserts entity`() = runTest {
        // given
        val post = Post(1, 1, "Title", "Body")
        val expectedEntity = post.toPostEntity()

        // when
        dataSource.insertPost(post)

        // then
        val captor = argumentCaptor<PostEntity>()
        verify(dao).insertPost(captor.capture())
        assertEquals(expectedEntity, captor.firstValue)
    }

    @Test(expected = RuntimeException::class)
    fun `insertPost propagates dao exception`() = runTest {
        // given
        val post = Post(1, 1, "Title", "Body")
        whenever(dao.insertPost(any())).thenThrow(RuntimeException("DB insert failed"))

        // when
        dataSource.insertPost(post) // should throw
    }
}
