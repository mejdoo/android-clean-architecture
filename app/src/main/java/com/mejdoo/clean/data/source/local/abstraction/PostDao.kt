package com.mejdoo.clean.data.source.local.abstraction

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.mejdoo.clean.data.model.PostEntity
import io.reactivex.Single


@Dao
interface PostDao {

    @Query("SELECT * from post")
    fun allPosts(): Single<List<PostEntity>>

    @Query("SELECT * from post WHERE id = :postId")
    fun postById(postId: Int): Single<PostEntity>

    @Insert(onConflict = REPLACE)
    fun insertPost(post: PostEntity)

}
