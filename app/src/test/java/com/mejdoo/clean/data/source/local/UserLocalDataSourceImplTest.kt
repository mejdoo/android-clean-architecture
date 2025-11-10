package com.mejdoo.clean.data.source.local

import com.mejdoo.clean.data.mapper.toUser
import com.mejdoo.clean.data.source.local.abstraction.UserDao
import com.mejdoo.clean.data.source.local.implementation.UserLocalDataSourceImpl
import com.mejdoo.clean.userEntity1
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations


class UserLocalDataSourceImplTest {

    @Mock
    private lateinit var mockDao: UserDao

    private lateinit var dataSource: UserLocalDataSourceImpl

    private val throwable = Throwable()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        dataSource = UserLocalDataSourceImpl(mockDao)
    }

    @Test
    fun test_UserById_Success() {

        val userId = 1

        `when`(mockDao.userById(userId)).thenReturn(Single.just(userEntity1))


        val test = dataSource.userById(userId).test()

        verify(mockDao).userById(userId)
        test.assertValue(userEntity1.toUser())
    }

    @Test
    fun test_UserById_Failure() {

        val userId = 1

        `when`(mockDao.userById(userId)).thenReturn(Single.error(throwable))

        val test = dataSource.userById(userId).test()

        verify(mockDao).userById(userId)
        test.assertError(throwable)
    }
}

