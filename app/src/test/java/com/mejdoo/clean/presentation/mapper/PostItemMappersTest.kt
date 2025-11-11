package com.mejdoo.clean.presentation.mapper

import com.mejdoo.clean.domain.model.Post
import org.junit.Assert.assertEquals
import org.junit.Test

class PostItemMappersTest {
    @Test
    fun `toPostItem should map Post to PostItem correctly`() {
        // Arrange
        val post =
            Post(
                id = 101,
                userId = 10,
                title = "Sample Post",
                body = "This is a post body",
            )

        // Act
        val postItem = post.toPostItem()

        // Assert
        assertEquals(post.id, postItem.postId)
        assertEquals(post.userId, postItem.userId)
        assertEquals(post.title, postItem.title)
    }

    @Test
    fun `toPostItemList should map list of Post to list of PostItem correctly`() {
        // Arrange
        val posts =
            listOf(
                Post(id = 101, userId = 10, title = "Post 1", body = "Body 1"),
                Post(id = 102, userId = 20, title = "Post 2", body = "Body 2"),
            )

        // Act
        val postItemList = posts.toPostItemList()

        // Assert
        assertEquals(posts.size, postItemList.size)

        posts.zip(postItemList).forEach { (post, postItem) ->
            assertEquals(post.id, postItem.postId)
            assertEquals(post.userId, postItem.userId)
            assertEquals(post.title, postItem.title)
        }
    }
}
