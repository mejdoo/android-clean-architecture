package  com.mejdoo.clean.data.repository


import com.mejdoo.clean.data.source.local.abstraction.PostLocalDataSource
import com.mejdoo.clean.data.source.remote.abstraction.PostRemoteDataSource
import com.mejdoo.clean.domain.model.Post
import com.mejdoo.clean.domain.repository.PostRepository
import io.reactivex.Single

class PostRepositoryImpl(
    private val remoteDataSource: PostRemoteDataSource,
    private val localDataSource: PostLocalDataSource
) : PostRepository {


    override fun allPosts(): Single<List<Post>> =
        remoteDataSource.allPosts()
            .doOnSuccess { it.forEach { post -> localDataSource.insertPost(post) } }
            .onErrorResumeNext { localDataSource.allPosts() }


    override fun postById(postId: Int): Single<Post> =
        remoteDataSource.postById(postId)
            .doOnSuccess { localDataSource.insertPost(it) }
            .onErrorResumeNext { localDataSource.postById(postId) }


}


