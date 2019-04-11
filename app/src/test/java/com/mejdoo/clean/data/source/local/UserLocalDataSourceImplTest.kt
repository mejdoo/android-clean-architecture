package com.mejdoo.clean.data.source.local

import com.mejdoo.clean.data.mapper.UserEntityMapper
import com.mejdoo.clean.data.source.local.abstraction.UserDao
import com.mejdoo.clean.data.source.local.implementation.UserLocalDataSourceImpl
import com.mejdoo.clean.userEntity1
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations


class UserLocalDataSourceImplTest {


    @Mock
    private lateinit var mockDao: UserDao

    private lateinit var dataSource: UserLocalDataSourceImpl

    private val mapper = UserEntityMapper()

    private val throwable = Throwable()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        dataSource = UserLocalDataSourceImpl(mockDao, mapper)
    }


    @Test
    fun test_GetUserById_Success() {

        val userId = 1

        `when`(mockDao.getUserById(userId)).thenReturn(Single.just(userEntity1))


        val test = dataSource.getUserById(userId).test()

        verify(mockDao).getUserById(userId)
        test.assertValue(mapper.mapToDomain(userEntity1))
    }

    @Test
    fun test_GetUserById_Failure() {

        val userId = 1

        `when`(mockDao.getUserById(userId)).thenReturn(Single.error(throwable))

        val test = dataSource.getUserById(userId).test()

        verify(mockDao).getUserById(userId)
        test.assertError(throwable)
    }

}

