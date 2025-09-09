package com.afaqy.cursortest.data.remote

import com.afaqy.cursortest.data.remote.dto.PostDto
import retrofit2.http.GET

interface ApiService {
    @GET("posts")
    suspend fun getPosts(): List<PostDto>
}



