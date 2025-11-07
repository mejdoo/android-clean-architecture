package com.mejdoo.clean.data.mapper


import com.mejdoo.clean.data.model.PostEntity
import com.mejdoo.clean.domain.model.Post
import org.junit.Assert.assertEquals
import org.junit.Test

class PostMappersTest {

    @Test
    fun `toDomain should map PostEntity to Post correctly`() {
        // Arrange
        val entity = PostEntity(
            userId = 10,
            id = 100,
            title = "Post Title",
            body = "Post body content"
        )

        // Act
        val domain = entity.toDomain()

        // Assert
        assertEquals(entity.userId, domain.userId)
        assertEquals(entity.id, domain.id)
        assertEquals(entity.title, domain.title)
        assertEquals(entity.body, domain.body)
    }

    @Test
    fun `toData should map Post to PostEntity correctly`() {
        // Arrange
        val post = Post(
            userId = 10,
            id = 100,
            title = "Post Title",
            body = "Post body content"
        )

        // Act
        val entity = post.toData()

        // Assert
        assertEquals(post.userId, entity.userId)
        assertEquals(post.id, entity.id)
        assertEquals(post.title, entity.title)
        assertEquals(post.body, entity.body)
    }

    @Test
    fun `toDomainList should map list of PostEntity to list of Post correctly`() {
        // Arrange
        val entities = listOf(
            PostEntity(1, 101, "Title 1", "Body 1"),
            PostEntity(2, 102, "Title 2", "Body 2")
        )

        // Act
        val domainList = entities.toDomainList()

        // Assert
        assertEquals(entities.size, domainList.size)
        entities.zip(domainList).forEach { (entity, domain) ->
            assertEquals(entity.userId, domain.userId)
            assertEquals(entity.id, domain.id)
            assertEquals(entity.title, domain.title)
            assertEquals(entity.body, domain.body)
        }
    }

    @Test
    fun `toDataList should map list of Post to list of PostEntity correctly`() {
        // Arrange
        val posts = listOf(
            Post(1, 101, "Title 1", "Body 1"),
            Post(2, 102, "Title 2", "Body 2")
        )

        // Act
        val entityList = posts.toDataList()

        // Assert
        assertEquals(posts.size, entityList.size)
        posts.zip(entityList).forEach { (domain, entity) ->
            assertEquals(domain.userId, entity.userId)
            assertEquals(domain.id, entity.id)
            assertEquals(domain.title, entity.title)
            assertEquals(domain.body, entity.body)
        }
    }
}
