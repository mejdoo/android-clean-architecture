package com.mejdoo.clean.data.source.remote

import com.mejdoo.clean.data.mapper.UserEntityMapper
import com.mejdoo.clean.data.source.remote.abstraction.CleanApi
import com.mejdoo.clean.data.source.remote.implementation.UserRemoteDataSourceImpl
import com.mejdoo.clean.userEntity1
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations


class UserRemoteDataSourceImplTest {

    @Mock
    private lateinit var mockApi: CleanApi

    private lateinit var dataSource: UserRemoteDataSourceImpl

    private val mapper = UserEntityMapper()

    private val throwable = Throwable()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        dataSource = UserRemoteDataSourceImpl(mockApi, mapper)
    }

    @Test
    fun test_GetUserById_Success() {

        val userId = 1

        `when`(mockApi.getUserById(userId)).thenReturn(Single.just(userEntity1))


        val test = dataSource.getUserById(userId).test()

        verify(mockApi).getUserById(userId)
        test.assertValue(mapper.mapToDomain(userEntity1))
    }

    @Test
    fun test_GetUserById_Failure() {

        val userId = 1

        `when`(mockApi.getUserById(userId)).thenReturn(Single.error(throwable))

        val test = dataSource.getUserById(userId).test()

        verify(mockApi).getUserById(userId)
        test.assertError(throwable)
    }

}

