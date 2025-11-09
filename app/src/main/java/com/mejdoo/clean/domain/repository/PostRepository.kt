package  com.mejdoo.clean.domain.repository

import com.mejdoo.clean.domain.model.Post
import io.reactivex.Single

interface PostRepository {
    fun allPosts(): Single<List<Post>>
    fun postById(postId: Int): Single<Post>
}
