package com.afaqy.cursortest.presentation.details

import com.afaqy.cursortest.domain.model.Post
import com.afaqy.cursortest.domain.repository.PostsRepository
import com.afaqy.cursortest.domain.usecase.GetPostsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DetailsViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Load intent updates state with posts`() = kotlinx.coroutines.runBlocking {
        val posts = listOf(Post(1, 1, "t", "b"))
        val repo = object : PostsRepository {
            override suspend fun getPosts(): List<Post> = posts
        }
        val useCase = GetPostsUseCase(repo)
        val vm = DetailsViewModel(useCase)

        vm.sendIntent(DetailsIntent.Load)
        dispatcher.scheduler.advanceUntilIdle()

        assertEquals(false, vm.state.value.isLoading)
        assertEquals(1, vm.state.value.posts.size)
        assertEquals("t", vm.state.value.posts.first().title)
    }
}
