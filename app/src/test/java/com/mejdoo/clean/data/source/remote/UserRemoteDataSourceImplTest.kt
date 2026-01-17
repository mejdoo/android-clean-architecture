package com.mejdoo.clean.data.source.remote

import com.mejdoo.clean.data.mapper.toUser
import com.mejdoo.clean.data.model.UserEntity
import com.mejdoo.clean.data.source.remote.abstraction.CleanApi
import com.mejdoo.clean.data.source.remote.implementation.UserRemoteDataSourceImpl
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class UserRemoteDataSourceImplTest {
    @Mock
    private lateinit var api: CleanApi

    private lateinit var dataSource: UserRemoteDataSourceImpl

    @Before
    fun setUp() {
        dataSource = UserRemoteDataSourceImpl(api)
    }

    // --------------------------------------------------------------------
    // userById()
    // --------------------------------------------------------------------

    @Test
    fun `userById returns mapped user from api`() = runTest {
        // given
        val userId = 1
        val apiResponse =
            UserEntity(userId, "Name 1", "email1@example.com", "+49000000", "example.com")
        val expectedUser = apiResponse.toUser()

        whenever(api.userById(userId)).thenReturn(apiResponse)

        // when
        val result = dataSource.userById(userId)

        // then
        assertEquals(expectedUser, result)
        verify(api).userById(userId)
    }

    @Test(expected = RuntimeException::class)
    fun `userById propagates api exception`() = runTest {
        val userId = 1
        whenever(api.userById(userId)).thenThrow(RuntimeException("Network error"))

        // when
        dataSource.userById(userId) // should throw
    }
}
