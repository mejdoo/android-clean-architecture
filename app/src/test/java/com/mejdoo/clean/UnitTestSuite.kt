package com.mejdoo.clean


import com.mejdoo.clean.data.mapper.CommentMappersTest
import com.mejdoo.clean.data.mapper.PostMappersTest
import com.mejdoo.clean.data.mapper.UserMappersTest
import com.mejdoo.clean.data.repository.CommentRepositoryImplTest
import com.mejdoo.clean.data.repository.PostRepositoryImplTest
import com.mejdoo.clean.data.repository.UserRepositoryImplTest
import com.mejdoo.clean.data.source.local.CommentLocalDataSourceImplTest
import com.mejdoo.clean.data.source.local.PostLocalDataSourceImplTest
import com.mejdoo.clean.data.source.local.UserLocalDataSourceImplTest
import com.mejdoo.clean.data.source.remote.CommentRemoteDataSourceImplTest
import com.mejdoo.clean.data.source.remote.PostRemoteDataSourceImplTest
import com.mejdoo.clean.data.source.remote.UserRemoteDataSourceImplTest
import com.mejdoo.clean.domain.usecase.PostListUseCaseTest
import com.mejdoo.clean.presentation.mapper.PostDetailMappersTest
import com.mejdoo.clean.presentation.mapper.PostItemMappersTest
import com.mejdoo.clean.presentation.viewmodel.PostDetailViewModelTest
import com.mejdoo.clean.presentation.viewmodel.PostListViewModelTest
import org.junit.runner.RunWith
import org.junit.runners.Suite


@RunWith(Suite::class)
@Suite.SuiteClasses(
    PostMappersTest::class,
    UserMappersTest::class,
    CommentMappersTest::class,
    PostRemoteDataSourceImplTest::class,
    UserRemoteDataSourceImplTest::class,
    CommentRemoteDataSourceImplTest::class,
    PostLocalDataSourceImplTest::class,
    UserLocalDataSourceImplTest::class,
    CommentLocalDataSourceImplTest::class,
    PostRepositoryImplTest::class,
    CommentRepositoryImplTest::class,
    UserRepositoryImplTest::class,
    PostListUseCaseTest::class,
    PostItemMappersTest::class,
    PostDetailMappersTest::class,
    PostListViewModelTest::class,
    PostDetailViewModelTest::class
)
class UnitTestSuite