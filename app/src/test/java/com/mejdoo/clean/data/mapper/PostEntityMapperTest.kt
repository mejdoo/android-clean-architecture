package com.mejdoo.clean.data.mapper


import com.mejdoo.clean.post1
import com.mejdoo.clean.postEntity1
import com.mejdoo.clean.postEntity2
import org.junit.Assert.assertTrue
import org.junit.Test

class PostEntityMapperTest {

    @Test
    fun test_MapToDomain() {

        val postMapper = PostEntityMapper()

        val post = postMapper.mapToDomain(postEntity1)

        assertTrue(post.userId == postEntity1.userId)
        assertTrue(post.id == postEntity1.id)
        assertTrue(post.title == postEntity1.title)
        assertTrue(post.body == postEntity1.body)
    }

    @Test
    fun test_MapListToDomain() {

        val postEntities = mutableListOf(postEntity1, postEntity2)

        val postMapper = PostEntityMapper()

        val posts = postMapper.mapListToDomain(postEntities)

        assertTrue(posts.size == postEntities.size)

        for (i in 0 until posts.size - 1) {

            assertTrue(posts[i].userId == postEntities[i].userId)
            assertTrue(posts[i].id == postEntities[i].id)
            assertTrue(posts[i].title == postEntities[i].title)
            assertTrue(posts[i].body == postEntities[i].body)

        }
    }


    @Test
    fun test_MapFromDomain() {

        val postMapper = PostEntityMapper()

        val postEntity = postMapper.mapFromDomain(post1)

        assertTrue(postEntity.userId == post1.userId)
        assertTrue(postEntity.id == post1.id)
        assertTrue(postEntity.title == post1.title)
        assertTrue(postEntity.body == post1.body)
    }

}