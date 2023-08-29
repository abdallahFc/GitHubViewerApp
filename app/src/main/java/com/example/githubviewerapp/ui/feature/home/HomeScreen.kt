package com.example.githubviewerapp.ui.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.githubviewerapp.ui.composables.ApplicationScaffold
import com.example.githubviewerapp.ui.composables.ContentVisibility
import com.example.githubviewerapp.ui.composables.NetworkError
import com.example.githubviewerapp.ui.composables.ShimmerLoading
import com.example.githubviewerapp.ui.feature.details.navigateToDetailsScreen
import com.example.githubviewerapp.ui.feature.home.composables.RepositoryItem
import com.example.githubviewerapp.ui.navigation.LocalNavigationProvider

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val navController = LocalNavigationProvider.current
    LaunchedEffect(key1 = true) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is HomeUiEffect.NavigateToRepositoryDetails -> {
                    navController.navigateToDetailsScreen(effect.owner, effect.repositoryName)
                }
            }
        }
    }
    HomeContent(state, viewModel)

}

@Composable
fun HomeContent(
    state: HomeUiState,
    listener: HomeInteractionListener
) {
    ApplicationScaffold(
        textToShow = "Repositories"
    ) {
        ShimmerLoading(state.isLoading)
        NetworkError(state = state.emptyPlaceHolder(), error = "No Internet found")
        ContentVisibility(state.contentScreen()) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(vertical = 16.dp)

            ) {
                items(state.repositories.size) { index ->
                    val repository = state.repositories[index]
                    RepositoryItem(
                        repositoryName = repository.name,
                        repositoryDescription = repository.description,
                        repositoryOwner = repository.user.userName,
                        ownerAvatarUrl = repository.user.userAvatarUrl
                    ) {
                        listener.onClickRepositoryItem(
                            repository.user.userName,
                            repository.name
                        )
                    }
                }
            }
        }
    }
}

