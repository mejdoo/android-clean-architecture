package com.mejdoo.clean.presentation.mapper

import com.mejdoo.clean.domain.usecase.CombinedPostUserComments
import com.mejdoo.clean.presentation.model.PostDetail


/**
 * Map a [PostDetail] from a [CombinedPostUserComments] instance when data is moving between
 * this layer and the Domain layer
 */
open class PostDetailMapper :
        Mapper<CombinedPostUserComments, PostDetail> {


    /**
     * Map a [PostDetail] instance from a [CombinedPostUserComments] instance
     */
    override fun mapFromDomain(entity: CombinedPostUserComments) =
            PostDetail(
                    entity.post.id,
                    entity.user.id,
                    entity.post.title,
                    entity.post.body,
                    entity.user.name,
                    entity.comments.size
            )

    /**
     * Map a list of [PostDetail] from a list of [CombinedPostUserComments]
     */
    override fun mapListFromDomain(list: List<CombinedPostUserComments>): List<PostDetail> =
            list.map { mapFromDomain(it) }


}