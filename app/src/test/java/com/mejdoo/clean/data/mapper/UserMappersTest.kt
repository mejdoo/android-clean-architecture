package com.mejdoo.clean.data.mapper


import com.mejdoo.clean.data.model.UserEntity
import com.mejdoo.clean.domain.model.User
import org.junit.Assert.assertEquals
import org.junit.Test

class UserMappersTest {

    @Test
    fun `toDomain should map UserEntity to User correctly`() {
        // Arrange
        val entity = UserEntity(
            id = 1,
            name = "Alice",
            email = "alice@mail.com",
            phone = "123-456-7890",
            website = "alice.com"
        )

        // Act
        val domain = entity.toDomain()

        // Assert
        assertEquals(entity.id, domain.id)
        assertEquals(entity.name, domain.name)
        assertEquals(entity.email, domain.email)
        assertEquals(entity.phone, domain.phone)
        assertEquals(entity.website, domain.website)
    }

    @Test
    fun `toData should map User to UserEntity correctly`() {
        // Arrange
        val user = User(
            id = 1,
            name = "Alice",
            email = "alice@mail.com",
            phone = "123-456-7890",
            website = "alice.com"
        )

        // Act
        val entity = user.toData()

        // Assert
        assertEquals(user.id, entity.id)
        assertEquals(user.name, entity.name)
        assertEquals(user.email, entity.email)
        assertEquals(user.phone, entity.phone)
        assertEquals(user.website, entity.website)
    }

    @Test
    fun `toDomainList should map list of UserEntity to list of User correctly`() {
        // Arrange
        val entities = listOf(
            UserEntity(1, "Alice", "alice@mail.com", "123-456", "alice.com"),
            UserEntity(2, "Bob", "bob@mail.com", "987-654", "bob.com")
        )

        // Act
        val domainList = entities.toDomainList()

        // Assert
        assertEquals(entities.size, domainList.size)
        entities.zip(domainList).forEach { (entity, domain) ->
            assertEquals(entity.id, domain.id)
            assertEquals(entity.name, domain.name)
            assertEquals(entity.email, domain.email)
            assertEquals(entity.phone, domain.phone)
            assertEquals(entity.website, domain.website)
        }
    }

    @Test
    fun `toDataList should map list of User to list of UserEntity correctly`() {
        // Arrange
        val users = listOf(
            User(1, "Alice", "alice@mail.com", "123-456", "alice.com"),
            User(2, "Bob", "bob@mail.com", "987-654", "bob.com")
        )

        // Act
        val entityList = users.toDataList()

        // Assert
        assertEquals(users.size, entityList.size)
        users.zip(entityList).forEach { (domain, entity) ->
            assertEquals(domain.id, entity.id)
            assertEquals(domain.name, entity.name)
            assertEquals(domain.email, entity.email)
            assertEquals(domain.phone, entity.phone)
            assertEquals(domain.website, entity.website)
        }
    }
}
