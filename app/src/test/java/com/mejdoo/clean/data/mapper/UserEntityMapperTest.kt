package com.mejdoo.clean.data.mapper


import com.mejdoo.clean.user1
import com.mejdoo.clean.userEntity1
import com.mejdoo.clean.userEntity2
import org.junit.Assert.assertTrue
import org.junit.Test

class UserEntityMapperTest {

    @Test
    fun test_MapToDomain() {

        val userMapper = UserEntityMapper()

        val user = userMapper.mapToDomain(userEntity1)

        assertTrue(user.id == userEntity1.id)
        assertTrue(user.name == userEntity1.name)
        assertTrue(user.email == userEntity1.email)
        assertTrue(user.phone == userEntity1.phone)
        assertTrue(user.website == userEntity1.website)
    }

    @Test
    fun test_MapListToDomain() {

        val userEntities = mutableListOf(userEntity1, userEntity2)

        val userMapper = UserEntityMapper()

        val users = userMapper.mapListToDomain(userEntities)

        assertTrue(users.size == userEntities.size)

        for (i in 0 until users.size - 1) {

            assertTrue(users[i].id == userEntities[i].id)
            assertTrue(users[i].name == userEntities[i].name)
            assertTrue(users[i].email == userEntities[i].email)
            assertTrue(users[i].phone == userEntities[i].phone)
            assertTrue(users[i].website == userEntities[i].website)
        }
    }


    @Test
    fun test_MapFromDomain() {

        val userMapper = UserEntityMapper()

        val userEntity = userMapper.mapFromDomain(user1)

        assertTrue(userEntity.id == user1.id)
        assertTrue(userEntity.name == user1.name)
        assertTrue(userEntity.email == user1.email)
        assertTrue(userEntity.phone == user1.phone)
        assertTrue(userEntity.website == user1.website)
    }

}