package com.afaqy.cursortest.data.repository

import com.afaqy.cursortest.data.remote.ApiService
import com.afaqy.cursortest.data.remote.dto.toDomain
import com.afaqy.cursortest.domain.model.Post
import com.afaqy.cursortest.domain.repository.PostsRepository

class PostsRepositoryImpl(
    private val api: ApiService
) : PostsRepository {
    override suspend fun getPosts(): List<Post> {
        return api.getPosts().map { it.toDomain() }
    }
}



