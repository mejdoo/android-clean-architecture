package com.mejdoo.clean.presentation.mapper


import com.mejdoo.clean.post1
import com.mejdoo.clean.post2
import org.junit.Assert.assertTrue
import org.junit.Test

class PostItemMapperTest {

    @Test
    fun test_MapFromDomain() {


        val postItemMapper = PostItemMapper()

        val postItem = postItemMapper.mapFromDomain(post1)


        assertTrue(postItem.postId == post1.id)
        assertTrue(postItem.userId == post1.userId)
        assertTrue(postItem.title == post1.title)
    }

    @Test
    fun test_MapListFromDomain() {


        val posts = mutableListOf(post1, post2)

        val postItemMapper = PostItemMapper()

        val postItems = postItemMapper.mapListFromDomain(posts)


        assertTrue(postItems.size == posts.size)

        for (i in 0 until postItems.size - 1) {

            assertTrue(postItems[i].postId == posts[i].id)
            assertTrue(postItems[i].userId == posts[i].userId)
            assertTrue(postItems[i].title == posts[i].title)

        }


    }
}