package com.afaqy.cursortest.data.remote.dto

import com.afaqy.cursortest.domain.model.Post

data class PostDto(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)

fun PostDto.toDomain(): Post = Post(
    userId = userId,
    id = id,
    title = title,
    body = body
)



