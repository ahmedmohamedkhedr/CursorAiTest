package com.afaqy.cursortest.data.repository

import com.afaqy.cursortest.data.remote.ApiService
import com.afaqy.cursortest.data.remote.dto.PostDto
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class PostsRepositoryImplTest {

    @Test
    fun `getPosts maps dto to domain`() = runBlocking {
        val api = mock<ApiService>()
        val dtos = listOf(
            PostDto(userId = 1, id = 1, title = "t1", body = "b1"),
            PostDto(userId = 2, id = 2, title = "t2", body = "b2")
        )
        whenever(api.getPosts()).thenReturn(dtos)

        val repo = PostsRepositoryImpl(api)
        val result = repo.getPosts()

        assertEquals(2, result.size)
        assertEquals("t1", result[0].title)
        assertEquals("b2", result[1].body)
    }
}
