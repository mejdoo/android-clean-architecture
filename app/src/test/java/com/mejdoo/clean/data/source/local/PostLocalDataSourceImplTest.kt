package com.mejdoo.clean.data.source.local

import com.mejdoo.clean.data.mapper.toPost
import com.mejdoo.clean.data.mapper.toPostList
import com.mejdoo.clean.data.source.local.abstraction.PostDao
import com.mejdoo.clean.data.source.local.implementation.PostLocalDataSourceImpl
import com.mejdoo.clean.postEntity1
import com.mejdoo.clean.postEntity2
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations


class PostLocalDataSourceImplTest {

    private lateinit var closeable: AutoCloseable

    @Mock
    private lateinit var mockDao: PostDao

    private lateinit var dataSource: PostLocalDataSourceImpl

    private val localList = listOf(postEntity1, postEntity2)

    private val throwable = Throwable()

    @Before
    fun setUp() {
        closeable = MockitoAnnotations.openMocks(this)
        dataSource = PostLocalDataSourceImpl(mockDao)
    }

    @After
    fun tearDown() {
        closeable.close()
    }

    @Test
    fun test_AllPosts_Success() {

        `when`(mockDao.allPosts()).thenReturn(Single.just(localList))


        val test = dataSource.allPosts().test()

        verify(mockDao).allPosts()
        test.assertValue(localList.toPostList())
    }

    @Test
    fun test_AllPosts_Failure() {

        `when`(mockDao.allPosts()).thenReturn(Single.error(throwable))

        val test = dataSource.allPosts().test()

        verify(mockDao).allPosts()
        test.assertError(throwable)
    }


    @Test
    fun test_PostById_Success() {

        val postId = 1

        `when`(mockDao.postById(postId)).thenReturn(Single.just(postEntity1))


        val test = dataSource.postById(postId).test()

        verify(mockDao).postById(postId)
        test.assertValue(postEntity1.toPost())
    }

    @Test
    fun test_PostById_Failure() {

        val postId = 1

        `when`(mockDao.postById(postId)).thenReturn(Single.error(throwable))

        val test = dataSource.postById(postId).test()

        verify(mockDao).postById(postId)
        test.assertError(throwable)
    }
}

