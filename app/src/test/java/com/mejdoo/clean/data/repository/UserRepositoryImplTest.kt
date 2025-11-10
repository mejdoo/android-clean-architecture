package com.mejdoo.clean.data.repository

import com.mejdoo.clean.data.source.local.abstraction.UserLocalDataSource
import com.mejdoo.clean.data.source.remote.abstraction.UserRemoteDataSource
import com.mejdoo.clean.user1
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class UserRepositoryImplTest {

    private lateinit var closeable: AutoCloseable

    private lateinit var repository: UserRepositoryImpl

    @Mock
    private lateinit var mockRemoteDataSource: UserRemoteDataSource

    @Mock
    private lateinit var mockLocalDataSource: UserLocalDataSource


    private val throwable = Throwable()

    @Before
    fun setUp() {
        closeable = MockitoAnnotations.openMocks(this)
        repository = UserRepositoryImpl(mockRemoteDataSource, mockLocalDataSource)
    }

    @After
    fun tearDown() {
        closeable.close()
    }

    @Test
    fun test_UserById_RemoteDataSource_Success() {

        val userId = 1

        `when`(mockRemoteDataSource.userById(userId)).thenReturn(Single.just(user1))


        val test = repository.userById(userId).test()

        verify(mockRemoteDataSource).userById(userId)
        test.assertValue(user1)
    }

    @Test
    fun test_UserById_RemoteDataSource_Failure_LocalDataSource_Success() {

        val userId = 1

        `when`(mockRemoteDataSource.userById(userId)).thenReturn(Single.error(throwable))
        `when`(mockLocalDataSource.userById(userId)).thenReturn(Single.just(user1))

        val test = repository.userById(userId).test()


        verify(mockRemoteDataSource).userById(userId)
        verify(mockLocalDataSource).userById(userId)

        test.assertValue(user1)
    }

    @Test
    fun test_UserById_RemoteDataSource_Failure_LocalDataSource_Failure() {

        val userId = 1

        `when`(mockRemoteDataSource.userById(userId)).thenReturn(Single.error(throwable))
        `when`(mockLocalDataSource.userById(userId)).thenReturn(Single.error(throwable))

        val test = repository.userById(userId).test()


        verify(mockRemoteDataSource).userById(userId)
        verify(mockLocalDataSource).userById(userId)

        test.assertError(throwable)
    }


}