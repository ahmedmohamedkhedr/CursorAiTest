package com.afaqy.cursortest.domain.usecase

import com.afaqy.cursortest.domain.model.Post
import com.afaqy.cursortest.domain.repository.PostsRepository

class GetPostsUseCase(
    private val repository: PostsRepository
) {
    suspend operator fun invoke(): List<Post> = repository.getPosts()
}



