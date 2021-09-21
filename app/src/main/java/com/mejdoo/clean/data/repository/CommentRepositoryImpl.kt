package  com.mejdoo.clean.data.repository


import com.mejdoo.clean.data.source.local.abstraction.CommentLocalDataSource
import com.mejdoo.clean.data.source.remote.abstraction.CommentRemoteDataSource
import com.mejdoo.clean.domain.model.Comment
import com.mejdoo.clean.domain.repository.CommentRepository
import io.reactivex.Single

class CommentRepositoryImpl constructor(
        private val remoteDataSource: CommentRemoteDataSource,
        private val localDataSource: CommentLocalDataSource
) : CommentRepository {

    override fun getCommentsByPostId(postId: Int): Single<List<Comment>> =

            remoteDataSource.getCommentsByPostId(postId)
                    .doOnSuccess { it.forEach { comment -> localDataSource.insertComment(comment) } }
                    .onErrorResumeNext { localDataSource.getCommentsByPostId(postId) }

}




