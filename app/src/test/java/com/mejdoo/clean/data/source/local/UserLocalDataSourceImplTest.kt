package com.mejdoo.clean.data.source.local

import com.mejdoo.clean.data.mapper.toUser
import com.mejdoo.clean.data.mapper.toUserEntity
import com.mejdoo.clean.data.model.UserEntity
import com.mejdoo.clean.data.source.local.abstraction.UserDao
import com.mejdoo.clean.data.source.local.implementation.UserLocalDataSourceImpl
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
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever


@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class UserLocalDataSourceImplTest {

    @Mock
    private lateinit var dao: UserDao

    private lateinit var dataSource: UserLocalDataSourceImpl

    @Before
    fun setUp() {
        dataSource = UserLocalDataSourceImpl(dao)
    }

    // --------------------------------------------------------------------
    // userById()
    // --------------------------------------------------------------------

    @Test
    fun `userById returns mapped user from dao`() = runTest {
        // given
        val userId = 1
        val entity = UserEntity(userId, "John Doe", "john@example.com", "+49000000", "example.com")
        val expectedUser = entity.toUser()

        whenever(dao.userById(userId)).thenReturn(flowOf(entity))

        // when
        val result = dataSource.userById(userId).first()

        // then
        assertEquals(expectedUser, result)
        verify(dao).userById(userId)
    }

    @Test(expected = RuntimeException::class)
    fun `userById propagates dao exception`() = runTest {
        // given
        val userId = 1
        whenever(dao.userById(userId)).thenThrow(RuntimeException("DB failure"))

        // when
        dataSource.userById(userId).first() // should throw
    }

    // --------------------------------------------------------------------
    // insertUser()
    // --------------------------------------------------------------------

    @Test
    fun `insertUser maps and inserts entity`() = runTest {
        // given
        val user = User(1, "John Doe", "john@example.com", "+49000000", "example.com")
        val expectedEntity = user.toUserEntity()

        // when
        dataSource.insertUser(user)

        // then
        val captor = argumentCaptor<UserEntity>()
        verify(dao).insertUser(captor.capture())
        assertEquals(expectedEntity, captor.firstValue)
    }

    @Test(expected = RuntimeException::class)
    fun `insertUser propagates dao exception`() = runTest {
        // given
        val user = User(1, "John Doe", "john@example.com", "+49000000", "example.com")
        whenever(dao.insertUser(any())).thenThrow(RuntimeException("DB insert failed"))

        // when
        dataSource.insertUser(user) // should throw
    }
}

