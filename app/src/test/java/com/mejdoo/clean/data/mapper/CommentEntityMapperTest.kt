package com.mejdoo.clean.data.mapper


import com.mejdoo.clean.comment1
import com.mejdoo.clean.commentEntity1
import com.mejdoo.clean.commentEntity2
import org.junit.Assert.assertTrue
import org.junit.Test

class CommentEntityMapperTest {

    @Test
    fun test_MapToDomain() {


        val commentMapper = CommentEntityMapper()

        val comment = commentMapper.mapToDomain(commentEntity1)

        assertTrue(comment.postId == commentEntity1.postId)
        assertTrue(comment.id == commentEntity1.id)
        assertTrue(comment.name == commentEntity1.name)
        assertTrue(comment.email == commentEntity1.email)
        assertTrue(comment.body == commentEntity1.body)
    }

    @Test
    fun test_MapListToDomain() {


        val commentEntities = mutableListOf(commentEntity1, commentEntity2)

        val commentMapper = CommentEntityMapper()

        val comments = commentMapper.mapListToDomain(commentEntities)

        assertTrue(comments.size == commentEntities.size)

        for (i in 0 until comments.size - 1) {


            assertTrue(comments[i].postId == commentEntities[i].postId)
            assertTrue(comments[i].id == commentEntities[i].id)
            assertTrue(comments[i].name == commentEntities[i].name)
            assertTrue(comments[i].email == commentEntities[i].email)
            assertTrue(comments[i].body == commentEntities[i].body)

        }
    }


    @Test
    fun test_MapFromDomain() {


        val commentMapper = CommentEntityMapper()

        val commentEntity = commentMapper.mapFromDomain(comment1)

        assertTrue(commentEntity.postId == comment1.postId)
        assertTrue(commentEntity.id == comment1.id)
        assertTrue(commentEntity.name == comment1.name)
        assertTrue(commentEntity.email == comment1.email)
        assertTrue(commentEntity.body == comment1.body)
    }

}