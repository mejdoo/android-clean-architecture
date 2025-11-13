package com.mejdoo.clean.data.repository

import com.mejdoo.clean.data.source.local.abstraction.UserLocalDataSource
import com.mejdoo.clean.data.source.remote.abstraction.UserRemoteDataSource
import com.mejdoo.clean.domain.model.User
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
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class UserRepositoryImplTest {

    @Mock
    private lateinit var remoteDataSource: UserRemoteDataSource

    @Mock
    private lateinit var localDataSource: UserLocalDataSource

    private lateinit var repository: UserRepositoryImpl

    @Before
    fun setUp() {
        repository = UserRepositoryImpl(remoteDataSource, localDataSource)
    }

    // --------------------------------------------------------------------
    // userById() tests
    // --------------------------------------------------------------------

    @Test
    fun `userById returns local data`() = runTest {
        // given
        val localUser = User(1, "Local Name", "local@email.com", "+49000000", "test.com")
        whenever(localDataSource.userById(1)).thenReturn(flowOf(localUser))
        whenever(remoteDataSource.userById(1))
            .thenReturn(User(1, "Remote Name", "remote@email.com", "+49000000", "test.com"))

        // when
        val result = repository.userById(1).first()

        // then
        assertEquals(localUser, result)
        verify(localDataSource).userById(1)
        verify(remoteDataSource).userById(1)
    }

    @Test
    fun `userById fetches remote user and inserts into local`() = runTest {
        // given
        val userId = 1
        val remoteUser = User(userId, "Remote Name", "remote@email.com", "+49000000", "test.com")
        val cachedUser =
            User(userId, "Old Cached Name", "cached@email.com", "+49000000", "test.com")

        whenever(localDataSource.userById(userId)).thenReturn(flowOf(cachedUser))
        whenever(remoteDataSource.userById(userId)).thenReturn(remoteUser)

        // when
        repository.userById(userId).first()

        // then
        verify(remoteDataSource).userById(userId)
        val captor = argumentCaptor<User>()
        verify(localDataSource).insertUser(captor.capture())
        assertEquals(remoteUser, captor.firstValue)
    }

    @Test
    fun `userById ignores remote exception and still emits cached user`() = runTest {
        // given
        val userId = 1
        val cachedUser = User(userId, "Cached User", "cached@email.com", "+49000000", "test.com")

        whenever(localDataSource.userById(userId)).thenReturn(flowOf(cachedUser))
        whenever(remoteDataSource.userById(userId)).thenThrow(RuntimeException("Network error"))

        // when
        val result = repository.userById(userId).first()

        // then
        assertEquals(cachedUser, result)
        verify(localDataSource, never()).insertUser(any())
    }
}
