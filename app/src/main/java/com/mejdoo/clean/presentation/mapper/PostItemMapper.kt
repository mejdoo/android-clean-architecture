package com.mejdoo.clean.presentation.mapper

import com.mejdoo.clean.domain.model.Post
import com.mejdoo.clean.presentation.model.PostItem


/**
 * Map a [PostItem] from a [Post] instance when data is moving between
 * this layer and the Domain layer
 */
open class PostItemMapper :
        Mapper<Post, PostItem> {


    /**
     * Map a [PostItem] instance from a [Post] instance
     */
    override fun mapFromDomain(entity: Post) =
            PostItem(
                    entity.id,
                    entity.userId,
                    entity.title
            )


    /**
     * Map a list of [PostItem] from a list of [Post]
     */
    override fun mapListFromDomain(list: List<Post>): List<PostItem> = list.map { mapFromDomain(it) }


}