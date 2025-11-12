package com.mejdoo.clean.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mejdoo.clean.domain.usecase.PostDetailUseCase
import com.mejdoo.clean.presentation.mapper.toPostDetail
import com.mejdoo.clean.presentation.model.PostDetail
import com.mejdoo.clean.presentation.model.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class PostDetailViewModel(
    private val postDetailUseCase: PostDetailUseCase
) : ViewModel() {

    private val _postDetailState = MutableStateFlow<UiState<PostDetail>>(UiState.Loading)
    val postDetailState: StateFlow<UiState<PostDetail>> = _postDetailState

    fun loadPostDetail(postId: Int, userId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            postDetailUseCase(postId, userId)
                .map { combined -> UiState.Success(combined.toPostDetail()) as UiState<PostDetail> }
                .catch { e -> emit(UiState.Error(e.message ?: "Unknown error")) }
                .collect { _postDetailState.value = it }
        }

    }
}