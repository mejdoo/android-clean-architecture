package com.mejdoo.clean.presentation.mapper


import com.mejdoo.clean.comment1
import com.mejdoo.clean.comment2
import com.mejdoo.clean.domain.usecase.CombinedPostUserComments
import com.mejdoo.clean.post1
import com.mejdoo.clean.user1
import org.junit.Assert.assertTrue
import org.junit.Test

class PostDetailMapperTest {

    @Test
    fun test_MapFromDomain() {

        val combinedPostUserComments =
            CombinedPostUserComments(
                post1,
                user1, listOf(comment1, comment2)
            )

        val postDetail = combinedPostUserComments.toPostDetail()

        assertTrue(postDetail.postId == combinedPostUserComments.post.id)
        assertTrue(postDetail.userId == combinedPostUserComments.user.id)
        assertTrue(postDetail.title == combinedPostUserComments.post.title)
        assertTrue(postDetail.body == combinedPostUserComments.post.body)
        assertTrue(postDetail.userName == combinedPostUserComments.user.name)
        assertTrue(postDetail.commentCount == combinedPostUserComments.comments.size)
    }

    @Test
    fun test_MapListFromDomain() {

        val combinedPostUserComments =
            CombinedPostUserComments(
                post1,
                user1, listOf(comment1, comment2)
            )

        val listCombinedPostUserComments = listOf(combinedPostUserComments)

        val listOfPostDetail = listCombinedPostUserComments.toPostDetailList()


        assertTrue(listOfPostDetail.size == listCombinedPostUserComments.size)

        for (i in 0 until listOfPostDetail.size - 1) {

            assertTrue(listOfPostDetail[i].postId == listCombinedPostUserComments[i].post.id)
            assertTrue(listOfPostDetail[i].userId == listCombinedPostUserComments[i].user.id)
            assertTrue(listOfPostDetail[i].title == listCombinedPostUserComments[i].post.title)
            assertTrue(listOfPostDetail[i].body == listCombinedPostUserComments[i].post.body)
            assertTrue(listOfPostDetail[i].userName == listCombinedPostUserComments[i].user.name)
            assertTrue(listOfPostDetail[i].commentCount == listCombinedPostUserComments[i].comments.size)

        }

    }
}