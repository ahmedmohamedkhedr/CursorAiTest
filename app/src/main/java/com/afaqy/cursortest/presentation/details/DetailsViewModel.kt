package com.afaqy.cursortest.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afaqy.cursortest.domain.usecase.GetPostsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val getPostsUseCase: GetPostsUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<DetailsState> = MutableStateFlow(DetailsState())
    val state: StateFlow<DetailsState> = _state

    fun sendIntent(intent: DetailsIntent) {
        when (intent) {
            is DetailsIntent.Load -> loadPosts()
        }
    }

    private fun loadPosts() {
        _state.value = _state.value.copy(isLoading = true, errorMessage = null)
        viewModelScope.launch {
            try {
                val posts = getPostsUseCase()
                _state.value = _state.value.copy(isLoading = false, posts = posts)
            } catch (t: Throwable) {
                _state.value = _state.value.copy(isLoading = false, errorMessage = t.message)
            }
        }
    }
}


