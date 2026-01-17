package com.mejdoo.clean.presentation.mapper

import com.mejdoo.clean.domain.model.Comment
import com.mejdoo.clean.domain.model.Post
import com.mejdoo.clean.domain.model.User
import com.mejdoo.clean.domain.usecase.CombinedPostUserComments
import org.junit.Assert.assertEquals
import org.junit.Test

class PostDetailMappersTest {
    @Test
    fun `toPostDetail should map CombinedPostUserComments to PostDetail correctly`() {
        // Arrange
        val combined =
            CombinedPostUserComments(
                post = Post(id = 101, userId = 10, title = "Post Title", body = "Post Body"),
                user =
                User(
                    id = 10,
                    name = "Alice",
                    email = "alice@mail.com",
                    phone = "123",
                    website = "alice.com"
                ),
                comments =
                listOf(
                    Comment(
                        postId = 101,
                        id = 1,
                        name = "Comment 1",
                        email = "c1@mail.com",
                        body = "Body 1"
                    ),
                    Comment(
                        postId = 101,
                        id = 2,
                        name = "Comment 2",
                        email = "c2@mail.com",
                        body = "Body 2"
                    )
                )
            )

        // Act
        val postDetail = combined.toPostDetail()

        // Assert
        assertEquals(combined.post.id, postDetail.postId)
        assertEquals(combined.user.id, postDetail.userId)
        assertEquals(combined.post.title, postDetail.title)
        assertEquals(combined.post.body, postDetail.body)
        assertEquals(combined.user.name, postDetail.userName)
        assertEquals(combined.comments.size, postDetail.commentCount)
    }

    val combinedList =
        listOf(
            CombinedPostUserComments(
                post =
                Post(
                    id = 101,
                    userId = 10,
                    title = "Post 1",
                    body = "Body 1"
                ),
                user =
                User(
                    id = 10,
                    name = "Ali",
                    email = "ali@mail.com",
                    phone = "123",
                    website = "ali.com"
                ),
                comments =
                listOf(
                    Comment(
                        postId = 101,
                        id = 1,
                        name = "C1",
                        email = "c1@mail.com",
                        body = "B1"
                    )
                )
            ),
            CombinedPostUserComments(
                post = Post(id = 102, userId = 20, title = "Post 2", body = "Body 2"),
                user =
                User(
                    id = 20,
                    name = "Bob",
                    email = "bob@mail.com",
                    phone = "456",
                    website = "bob.com"
                ),
                comments =
                listOf(
                    Comment(
                        postId = 102,
                        id = 2,
                        name = "C2",
                        email = "c2@mail.com",
                        body = "B2"
                    ),
                    Comment(
                        postId = 102,
                        id = 3,
                        name = "C3",
                        email = "c3@mail.com",
                        body = "B3"
                    )
                )
            )
        )

    @Test
    fun `toPostDetailList should map list of CombinedPostUserComments to list of PostDetail correctly`() {
        // Act
        val postDetailList = combinedList.toPostDetailList()

        // Assert
        assertEquals(combinedList.size, postDetailList.size)

        combinedList.zip(postDetailList).forEach { (combined, postDetail) ->
            assertEquals(combined.post.id, postDetail.postId)
            assertEquals(combined.user.id, postDetail.userId)
            assertEquals(combined.post.title, postDetail.title)
            assertEquals(combined.post.body, postDetail.body)
            assertEquals(combined.user.name, postDetail.userName)
            assertEquals(combined.comments.size, postDetail.commentCount)
        }
    }
}
