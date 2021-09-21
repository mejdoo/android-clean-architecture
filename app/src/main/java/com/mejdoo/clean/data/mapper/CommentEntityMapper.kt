package com.mejdoo.clean.data.mapper

import com.mejdoo.clean.data.model.CommentEntity
import com.mejdoo.clean.domain.model.Comment

/**
 * Map a [CommentEntity] to a [Comment] instance when data is moving between
 * this layer and the Domain layer
 */
open class CommentEntityMapper :
        Mapper<CommentEntity, Comment> {

    /**
     * Map a [CommentEntity] instance to a [Comment] instance
     */
    override fun mapToDomain(entity: CommentEntity): Comment =
            Comment(
                    entity.postId,
                    entity.id,
                    entity.name,
                    entity.email,
                    entity.body
            )

    /**
     * Map a list of [CommentEntity] to a  list of [Comment]
     */
    override fun mapListToDomain(list: List<CommentEntity>): List<Comment> = list.map { mapToDomain(it) }


    /**
     * Map a [Comment] instance to a [CommentEntity] instance
     */
    override fun mapFromDomain(model: Comment): CommentEntity =
            CommentEntity(
                    model.postId,
                    model.id,
                    model.name,
                    model.email,
                    model.body
            )

}