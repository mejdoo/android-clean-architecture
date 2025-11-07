package com.mejdoo.clean.data.source.local

import com.mejdoo.clean.data.mapper.toDomain
import com.mejdoo.clean.data.mapper.toDomainList
import com.mejdoo.clean.data.source.local.abstraction.PostDao
import com.mejdoo.clean.data.source.local.implementation.PostLocalDataSourceImpl
import com.mejdoo.clean.postEntity1
import com.mejdoo.clean.postEntity2
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations


class PostLocalDataSourceImplTest {

    @Mock
    private lateinit var mockDao: PostDao

    private lateinit var dataSource: PostLocalDataSourceImpl

    private val localList = listOf(postEntity1, postEntity2)

    private val throwable = Throwable()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        dataSource = PostLocalDataSourceImpl(mockDao)
    }

    @Test
    fun test_GetAllPosts_Success() {

        `when`(mockDao.getAllPosts()).thenReturn(Single.just(localList))


        val test = dataSource.getAllPosts().test()

        verify(mockDao).getAllPosts()
        test.assertValue(localList.toDomainList())
    }

    @Test
    fun test_GetAllPosts_Failure() {

        `when`(mockDao.getAllPosts()).thenReturn(Single.error(throwable))

        val test = dataSource.getAllPosts().test()

        verify(mockDao).getAllPosts()
        test.assertError(throwable)
    }


    @Test
    fun test_GetPostById_Success() {

        val postId = 1

        `when`(mockDao.getPostById(postId)).thenReturn(Single.just(postEntity1))


        val test = dataSource.getPostById(postId).test()

        verify(mockDao).getPostById(postId)
        test.assertValue(postEntity1.toDomain())
    }

    @Test
    fun test_GetPostById_Failure() {

        val postId = 1

        `when`(mockDao.getPostById(postId)).thenReturn(Single.error(throwable))

        val test = dataSource.getPostById(postId).test()

        verify(mockDao).getPostById(postId)
        test.assertError(throwable)
    }
}

