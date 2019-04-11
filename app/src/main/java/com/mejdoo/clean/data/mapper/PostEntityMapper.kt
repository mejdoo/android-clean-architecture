package com.mejdoo.clean.data.mapper

import com.mejdoo.clean.data.model.PostEntity
import com.mejdoo.clean.domain.model.Post


/**
 * Map a [PostEntity] to a [Post] instance when data is moving between
 * this layer and the Domain layer
 */
open class PostEntityMapper :
    Mapper<PostEntity, Post> {


    /**
     * Map a [PostEntity] instance to a [Post] instance
     */
    override fun mapToDomain(entity: PostEntity): Post =
        Post(
            entity.userId,
            entity.id,
            entity.title,
            entity.body
        )

    /**
     * Map a list of [PostEntity] to a  list of [Post]
     */
    override fun mapListToDomain(list: List<PostEntity>): List<Post> = list.map { mapToDomain(it) }


    /**
     * Map a [Post] instance to a [PostEntity] instance
     */
    override fun mapFromDomain(model: Post): PostEntity =
        PostEntity(
            model.userId,
            model.id,
            model.title,
            model.body
        )
}