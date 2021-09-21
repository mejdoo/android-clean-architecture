package com.mejdoo.clean


import com.mejdoo.clean.data.mapper.CommentEntityMapperTest
import com.mejdoo.clean.data.mapper.PostEntityMapperTest
import com.mejdoo.clean.data.mapper.UserEntityMapperTest
import com.mejdoo.clean.data.repository.CommentRepositoryImplTest
import com.mejdoo.clean.data.repository.PostRepositoryImplTest
import com.mejdoo.clean.data.repository.UserRepositoryImplTest
import com.mejdoo.clean.data.source.local.CommentLocalDataSourceImplTest
import com.mejdoo.clean.data.source.local.PostLocalDataSourceImplTest
import com.mejdoo.clean.data.source.local.UserLocalDataSourceImplTest
import com.mejdoo.clean.data.source.remote.CommentRemoteDataSourceImplTest
import com.mejdoo.clean.data.source.remote.PostRemoteDataSourceImplTest
import com.mejdoo.clean.data.source.remote.UserRemoteDataSourceImplTest
import com.mejdoo.clean.presentation.mapper.PostDetailMapperTest
import com.mejdoo.clean.presentation.mapper.PostItemMapperTest
import com.mejdoo.clean.presentation.viewmodel.PostDetailViewModelTest
import com.mejdoo.clean.presentation.viewmodel.PostListViewModelTest
import com.mejdoo.clean.domain.usecase.PostListUseCaseTest
import org.junit.runner.RunWith
import org.junit.runners.Suite


@RunWith(Suite::class)
@Suite.SuiteClasses(
        PostEntityMapperTest::class,
        UserEntityMapperTest::class,
        CommentEntityMapperTest::class,
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
        PostItemMapperTest::class,
        PostDetailMapperTest::class,
        PostListViewModelTest::class,
        PostDetailViewModelTest::class
)
class UnitTestSuite