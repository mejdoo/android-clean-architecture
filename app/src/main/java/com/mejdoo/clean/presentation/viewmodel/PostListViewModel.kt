package com.mejdoo.clean.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mejdoo.clean.domain.model.Post
import com.mejdoo.clean.domain.usecase.PostListUseCase
import com.mejdoo.clean.presentation.mapper.toPostItemList
import com.mejdoo.clean.presentation.model.PostItem
import com.mejdoo.clean.presentation.model.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class PostListViewModel(postListUseCase: PostListUseCase) : ViewModel() {
    val postItemsState: StateFlow<UiState<List<PostItem>>> =
        postListUseCase()
            .map<List<Post>, UiState<List<PostItem>>> { posts ->
                UiState.Success(posts.toPostItemList())
            }.catch { e ->
                emit(UiState.Error(e.message ?: "Unknown error"))
            }.flowOn(Dispatchers.IO)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Lazily,
                initialValue = UiState.Loading
            )
}
