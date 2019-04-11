package com.mejdoo.clean.di

import androidx.room.Room
import com.mejdoo.clean.data.mapper.CommentEntityMapper
import com.mejdoo.clean.data.mapper.PostEntityMapper
import com.mejdoo.clean.data.mapper.UserEntityMapper
import com.mejdoo.clean.data.repository.CommentRepositoryImpl
import com.mejdoo.clean.data.repository.PostRepositoryImpl
import com.mejdoo.clean.data.repository.UserRepositoryImpl
import com.mejdoo.clean.data.source.local.abstraction.CommentLocalDataSource
import com.mejdoo.clean.data.source.local.abstraction.CleanDatabase
import com.mejdoo.clean.data.source.local.abstraction.PostLocalDataSource
import com.mejdoo.clean.data.source.local.abstraction.UserLocalDataSource
import com.mejdoo.clean.data.source.local.implementation.CommentLocalDataSourceImpl
import com.mejdoo.clean.data.source.local.implementation.PostLocalDataSourceImpl
import com.mejdoo.clean.data.source.local.implementation.UserLocalDataSourceImpl
import com.mejdoo.clean.data.source.remote.abstraction.CommentRemoteDataSource
import com.mejdoo.clean.data.source.remote.abstraction.PostRemoteDataSource
import com.mejdoo.clean.data.source.remote.abstraction.UserRemoteDataSource
import com.mejdoo.clean.data.source.remote.implementation.CommentRemoteDataSourceImpl
import com.mejdoo.clean.data.source.remote.implementation.PostRemoteDataSourceImpl
import com.mejdoo.clean.data.source.remote.implementation.UserRemoteDataSourceImpl
import com.mejdoo.clean.data.source.remote.implementation.cleanApi
import com.mejdoo.clean.domain.repository.CommentRepository
import com.mejdoo.clean.domain.repository.PostRepository
import com.mejdoo.clean.domain.repository.UserRepository
import com.mejdoo.clean.domain.usecase.PostDetailUseCase
import com.mejdoo.clean.domain.usecase.PostListUseCase
import com.mejdoo.clean.presentation.mapper.PostDetailMapper
import com.mejdoo.clean.presentation.mapper.PostItemMapper
import com.mejdoo.clean.presentation.viewmodel.PostDetailViewModel
import com.mejdoo.clean.presentation.viewmodel.PostListViewModel
import com.mejdoo.clean.util.DB_NAME
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module


val viewModelModule: Module = module {
    viewModel { PostListViewModel(get(), get()) }
    viewModel { PostDetailViewModel(get(), get()) }
}

val useCaseModule: Module = module {
    factory { PostListUseCase(get()) }
    factory { PostDetailUseCase(get(), get(), get()) }
}

val repositoryModule: Module = module {
    single {
        PostRepositoryImpl(
            get(),
            get()
        ) as PostRepository
    }
    single {
        UserRepositoryImpl(
            get(),
            get()
        ) as UserRepository
    }
    single {
        CommentRepositoryImpl(
            get(),
            get()
        ) as CommentRepository
    }
}

val dataSourceModule: Module = module {
    single {
        PostRemoteDataSourceImpl(
            api = cleanApi,
            mapper = get()
        ) as PostRemoteDataSource
    }
    single {
        UserRemoteDataSourceImpl(
            api = cleanApi,
            mapper = get()
        ) as UserRemoteDataSource
    }
    single {
        CommentRemoteDataSourceImpl(
            api = cleanApi,
            mapper = get()
        ) as CommentRemoteDataSource
    }
    single {
        PostLocalDataSourceImpl(
            get(),
            get()
        ) as PostLocalDataSource
    }
    single {
        UserLocalDataSourceImpl(
            get(),
            get()
        ) as UserLocalDataSource
    }
    single {
        CommentLocalDataSourceImpl(
            get(),
            get()
        ) as CommentLocalDataSource
    }
}

val networkModule: Module = module {
    single { cleanApi }
}

val cacheModule: Module = module {
    single { Room.databaseBuilder(androidApplication(), CleanDatabase::class.java, DB_NAME).build() }
    single { get<CleanDatabase>().postDao() }
    single { get<CleanDatabase>().userDao() }
    single { get<CleanDatabase>().commentDao() }
}

val mapperModule: Module = module {
    single { PostEntityMapper() }
    single { UserEntityMapper() }
    single { CommentEntityMapper() }
    single { PostItemMapper() }
    single { PostDetailMapper() }
}