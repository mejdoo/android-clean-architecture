package  com.mejdoo.clean.data.repository


import com.mejdoo.clean.data.source.local.abstraction.PostLocalDataSource
import com.mejdoo.clean.data.source.remote.abstraction.PostRemoteDataSource
import com.mejdoo.clean.domain.model.Post
import com.mejdoo.clean.domain.repository.PostRepository
import io.reactivex.Single

class PostRepositoryImpl constructor(
    private val remoteDataSource: PostRemoteDataSource,
    private val localDataSource: PostLocalDataSource
) : PostRepository {


    override fun getAllPosts(): Single<List<Post>> =
        remoteDataSource.getAllPosts()
            .doOnSuccess { it.forEach { post -> localDataSource.insertPost(post) } }
            .onErrorResumeNext { localDataSource.getAllPosts() }


    override fun getPostById(postId: Int): Single<Post> =
        remoteDataSource.getPostById(postId)
            .doOnSuccess { localDataSource.insertPost(it) }
            .onErrorResumeNext { localDataSource.getPostById(postId) }


}


