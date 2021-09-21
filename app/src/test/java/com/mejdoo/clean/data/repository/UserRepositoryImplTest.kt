package com.mejdoo.clean.data.repository

import com.mejdoo.clean.data.source.local.abstraction.UserLocalDataSource
import com.mejdoo.clean.data.source.remote.abstraction.UserRemoteDataSource
import com.mejdoo.clean.user1
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class UserRepositoryImplTest {

    private lateinit var repository: UserRepositoryImpl

    @Mock
    private lateinit var mockRemoteDataSource: UserRemoteDataSource

    @Mock
    private lateinit var mockLocalDataSource: UserLocalDataSource


    private val throwable = Throwable()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        repository = UserRepositoryImpl(mockRemoteDataSource, mockLocalDataSource)
    }

    @Test
    fun test_GetUserById_RemoteDataSource_Success() {

        val userId = 1

        `when`(mockRemoteDataSource.getUserById(userId)).thenReturn(Single.just(user1))


        val test = repository.getUserById(userId).test()

        verify(mockRemoteDataSource).getUserById(userId)
        test.assertValue(user1)
    }

    @Test
    fun test_GetUserById_RemoteDataSource_Failure_LocalDataSource_Success() {

        val userId = 1

        `when`(mockRemoteDataSource.getUserById(userId)).thenReturn(Single.error(throwable))
        `when`(mockLocalDataSource.getUserById(userId)).thenReturn(Single.just(user1))

        val test = repository.getUserById(userId).test()


        verify(mockRemoteDataSource).getUserById(userId)
        verify(mockLocalDataSource).getUserById(userId)

        test.assertValue(user1)
    }

    @Test
    fun test_GetUserById_RemoteDataSource_Failure_LocalDataSource_Failure() {

        val userId = 1

        `when`(mockRemoteDataSource.getUserById(userId)).thenReturn(Single.error(throwable))
        `when`(mockLocalDataSource.getUserById(userId)).thenReturn(Single.error(throwable))

        val test = repository.getUserById(userId).test()


        verify(mockRemoteDataSource).getUserById(userId)
        verify(mockLocalDataSource).getUserById(userId)

        test.assertError(throwable)
    }


}