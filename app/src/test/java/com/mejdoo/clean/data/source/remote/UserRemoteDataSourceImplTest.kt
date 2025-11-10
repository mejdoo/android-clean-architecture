package com.mejdoo.clean.data.source.remote

import com.mejdoo.clean.data.mapper.toUser
import com.mejdoo.clean.data.source.remote.abstraction.CleanApi
import com.mejdoo.clean.data.source.remote.implementation.UserRemoteDataSourceImpl
import com.mejdoo.clean.userEntity1
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations


class UserRemoteDataSourceImplTest {

    private lateinit var closeable: AutoCloseable

    @Mock
    private lateinit var mockApi: CleanApi

    private lateinit var dataSource: UserRemoteDataSourceImpl
    private val throwable = Throwable()

    @Before
    fun setUp() {
        closeable = MockitoAnnotations.openMocks(this)
        dataSource = UserRemoteDataSourceImpl(mockApi)
    }

    @After
    fun tearDown() {
        closeable.close()
    }

    @Test
    fun test_UserById_Success() {

        val userId = 1

        `when`(mockApi.userById(userId)).thenReturn(Single.just(userEntity1))


        val test = dataSource.userById(userId).test()

        verify(mockApi).userById(userId)
        test.assertValue(userEntity1.toUser())
    }

    @Test
    fun test_UserById_Failure() {

        val userId = 1

        `when`(mockApi.userById(userId)).thenReturn(Single.error(throwable))

        val test = dataSource.userById(userId).test()

        verify(mockApi).userById(userId)
        test.assertError(throwable)
    }

}

