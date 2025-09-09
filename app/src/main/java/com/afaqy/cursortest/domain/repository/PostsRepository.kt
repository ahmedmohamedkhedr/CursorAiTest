package com.afaqy.cursortest.domain.repository

import com.afaqy.cursortest.domain.model.Post

interface PostsRepository {
    suspend fun getPosts(): List<Post>
}



