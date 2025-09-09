package com.afaqy.cursortest.presentation.details

import com.afaqy.cursortest.domain.model.Post

data class DetailsState(
    val isLoading: Boolean = false,
    val posts: List<Post> = emptyList(),
    val errorMessage: String? = null
)


