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

        val postDetailMapper = PostDetailMapper()

        val postDetail = postDetailMapper.mapFromDomain(combinedPostUserComments)

        assertTrue(postDetail.postId == combinedPostUserComments.post.id)
        assertTrue(postDetail.userId == combinedPostUserComments.user.id)
        assertTrue(postDetail.title == combinedPostUserComments.post.title)
        assertTrue(postDetail.body == combinedPostUserComments.post.body)
        assertTrue(postDetail.username == combinedPostUserComments.user.name)
        assertTrue(postDetail.nbComments == combinedPostUserComments.comments.size)
    }

    @Test
    fun test_MapListFromDomain() {

        val combinedPostUserComments =
                CombinedPostUserComments(
                        post1,
                        user1, listOf(comment1, comment2)
                )

        val listCombinedPostUserComments = listOf(combinedPostUserComments)

        val postDetailMapper = PostDetailMapper()

        val listOfPostDetail = postDetailMapper.mapListFromDomain(listCombinedPostUserComments)


        assertTrue(listOfPostDetail.size == listCombinedPostUserComments.size)

        for (i in 0 until listOfPostDetail.size - 1) {

            assertTrue(listOfPostDetail[i].postId == listCombinedPostUserComments[i].post.id)
            assertTrue(listOfPostDetail[i].userId == listCombinedPostUserComments[i].user.id)
            assertTrue(listOfPostDetail[i].title == listCombinedPostUserComments[i].post.title)
            assertTrue(listOfPostDetail[i].body == listCombinedPostUserComments[i].post.body)
            assertTrue(listOfPostDetail[i].username == listCombinedPostUserComments[i].user.name)
            assertTrue(listOfPostDetail[i].nbComments == listCombinedPostUserComments[i].comments.size)

        }

    }
}