package com.mejdoo.clean.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mejdoo.clean.domain.usecase.CombinedPostUserComments
import com.mejdoo.clean.domain.usecase.PostDetailUseCase
import com.mejdoo.clean.domain.usecase.PostListUseCase
import com.mejdoo.clean.presentation.mapper.toPostDetail
import com.mejdoo.clean.presentation.model.PostDetail
import com.mejdoo.clean.presentation.model.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class FeedViewModel(private val postListUseCase: PostListUseCase, private val postDetailUseCase: PostDetailUseCase) : ViewModel() {
    private val _state = MutableStateFlow<UiState<List<PostDetail>>>(UiState.Loading)
    val state: StateFlow<UiState<List<PostDetail>>> = _state

    // selected details (includes comments)
    private val _selectedDetails = MutableStateFlow<UiState<CombinedPostUserComments>?>(null)
    val selectedDetails: StateFlow<UiState<CombinedPostUserComments>?> = _selectedDetails

    init {
        reload()
    }

    @Suppress("TooGenericExceptionCaught")
    fun reload() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = UiState.Loading
            try {
                val posts = postListUseCase().first()
                val details = posts.map { post ->
                    // combine post with its details (user + comments) via postDetailUseCase
                    postDetailUseCase(post.id, post.userId).first().toPostDetail()
                }
                _state.value = UiState.Success(details)
            } catch (e: Exception) {
                _state.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    @Suppress("TooGenericExceptionCaught")
    fun loadPostDetails(postId: Int, userId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _selectedDetails.value = UiState.Loading
            try {
                val combined = postDetailUseCase(postId, userId).first()
                _selectedDetails.value = UiState.Success(combined)
            } catch (e: Exception) {
                _selectedDetails.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}
