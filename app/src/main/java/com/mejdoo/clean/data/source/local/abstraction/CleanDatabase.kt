package com.mejdoo.clean.data.source.local.abstraction

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mejdoo.clean.data.model.CommentEntity
import com.mejdoo.clean.data.model.PostEntity
import com.mejdoo.clean.data.model.UserEntity

@Database(entities = [PostEntity::class, UserEntity::class, CommentEntity::class], version = 1, exportSchema = false)
abstract class CleanDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao
    abstract fun userDao(): UserDao
    abstract fun commentDao(): CommentDao
}