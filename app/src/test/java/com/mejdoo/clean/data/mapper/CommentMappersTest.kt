package com.mejdoo.clean.data.mapper


import com.mejdoo.clean.data.model.CommentEntity
import com.mejdoo.clean.domain.model.Comment
import org.junit.Assert.assertEquals
import org.junit.Test

class CommentMappersTest {


    @Test
    fun `toDomain should map CommentEntity to Comment correctly`() {
        // Arrange
        val entity = CommentEntity(
            postId = 1,
            id = 100,
            name = "John Doe",
            email = "john@example.com",
            body = "This is a comment."
        )

        // Act
        val domain = entity.toDomain()

        // Assert
        assertEquals(entity.postId, domain.postId)
        assertEquals(entity.id, domain.id)
        assertEquals(entity.name, domain.name)
        assertEquals(entity.email, domain.email)
        assertEquals(entity.body, domain.body)
    }

    @Test
    fun `toData should map Comment to CommentEntity correctly`() {
        // Arrange
        val comment = Comment(
            postId = 1,
            id = 100,
            name = "John Doe",
            email = "john@example.com",
            body = "This is a comment."
        )

        // Act
        val entity = comment.toData()

        // Assert
        assertEquals(comment.postId, entity.postId)
        assertEquals(comment.id, entity.id)
        assertEquals(comment.name, entity.name)
        assertEquals(comment.email, entity.email)
        assertEquals(comment.body, entity.body)
    }

    @Test
    fun `toDomainList should map list of CommentEntity to list of Comment correctly`() {
        // Arrange
        val entities = listOf(
            CommentEntity(1, 1, "Alice", "alice@mail.com", "First body"),
            CommentEntity(2, 2, "Bob", "bob@mail.com", "Second body")
        )

        // Act
        val domainList = entities.toDomainList()

        // Assert
        assertEquals(entities.size, domainList.size)
        entities.zip(domainList).forEach { (entity, domain) ->
            assertEquals(entity.postId, domain.postId)
            assertEquals(entity.id, domain.id)
            assertEquals(entity.name, domain.name)
            assertEquals(entity.email, domain.email)
            assertEquals(entity.body, domain.body)
        }
    }

    @Test
    fun `toDataList should map list of Comment to list of CommentEntity correctly`() {
        // Arrange
        val comments = listOf(
            Comment(1, 1, "Alice", "alice@mail.com", "First body"),
            Comment(2, 2, "Bob", "bob@mail.com", "Second body")
        )

        // Act
        val entityList = comments.toDataList()

        // Assert
        assertEquals(comments.size, entityList.size)
        comments.zip(entityList).forEach { (domain, entity) ->
            assertEquals(domain.postId, entity.postId)
            assertEquals(domain.id, entity.id)
            assertEquals(domain.name, entity.name)
            assertEquals(domain.email, entity.email)
            assertEquals(domain.body, entity.body)
        }
    }
}