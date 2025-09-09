package com.afaqy.cursortest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.afaqy.cursortest.data.remote.RetrofitProvider
import com.afaqy.cursortest.data.repository.PostsRepositoryImpl
import com.afaqy.cursortest.domain.model.Post
import com.afaqy.cursortest.domain.usecase.GetPostsUseCase
import com.afaqy.cursortest.presentation.details.DetailsIntent
import com.afaqy.cursortest.presentation.details.DetailsViewModel
import com.afaqy.cursortest.ui.theme.CursorTestTheme

class DetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Simple manual wiring (no DI framework)
        val api = RetrofitProvider.apiService
        val repository = PostsRepositoryImpl(api)
        val useCase = GetPostsUseCase(repository)

        setContent {
            CursorTestTheme {
                val vm: DetailsViewModel = viewModel(factory = object : ViewModelProvider.Factory {
                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                        @Suppress("UNCHECKED_CAST")
                        return DetailsViewModel(useCase) as T
                    }
                })

                DetailsScreen(viewModel = vm)
            }
        }
    }
}

@Composable
fun DetailsScreen(viewModel: DetailsViewModel) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.sendIntent(DetailsIntent.Load)
    }

    PostsList(
        posts = state.posts,
        isLoading = state.isLoading,
        errorMessage = state.errorMessage
    )
}

@Composable
private fun PostsList(posts: List<Post>, isLoading: Boolean, errorMessage: String?) {
    when {
        isLoading -> Text(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(align = Alignment.Center),
            text = "Loading...",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )

        errorMessage != null -> Text(
            text = errorMessage,
            style = MaterialTheme.typography.bodyLarge
        )

        else -> LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(12.dp)
        ) {
            items(posts) { post ->
                PostItem(post)
            }
        }
    }
}

@Composable
private fun PostItem(post: Post) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .padding(top = 8.dp),
            text = post.title,
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            text = post.body,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

