package  com.mejdoo.clean.domain.repository

import com.mejdoo.clean.domain.model.Post
import io.reactivex.Single

interface PostRepository {
    fun getAllPosts(): Single<List<Post>>
    fun getPostById(postId: Int): Single<Post>
}
