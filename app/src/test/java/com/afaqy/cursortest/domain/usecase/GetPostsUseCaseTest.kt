package com.afaqy.cursortest.domain.usecase

import com.afaqy.cursortest.domain.model.Post
import com.afaqy.cursortest.domain.repository.PostsRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetPostsUseCaseTest {

    @Test
    fun `invoke returns posts from repository`() = runBlocking {
        val repository = mock<PostsRepository>()
        val expected = listOf(
            Post(userId = 1, id = 1, title = "t1", body = "b1"),
            Post(userId = 2, id = 2, title = "t2", body = "b2")
        )
        whenever(repository.getPosts()).thenReturn(expected)

        val useCase = GetPostsUseCase(repository)

        val result = useCase()

        assertEquals(expected, result)
    }
}
