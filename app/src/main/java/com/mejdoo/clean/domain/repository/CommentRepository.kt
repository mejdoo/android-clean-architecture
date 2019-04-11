package  com.mejdoo.clean.domain.repository

import com.mejdoo.clean.domain.model.Comment
import io.reactivex.Single


interface CommentRepository {


    fun getCommentsByPostId(postId: Int): Single<List<Comment>>

}
