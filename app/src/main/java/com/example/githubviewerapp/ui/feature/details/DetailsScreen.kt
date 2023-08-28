package com.example.githubviewerapp.ui.feature.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.githubviewerapp.R
import com.example.githubviewerapp.ui.composables.ApplicationScaffold
import com.example.githubviewerapp.ui.composables.ContentVisibility
import com.example.githubviewerapp.ui.composables.Loading
import com.example.githubviewerapp.ui.composables.NetworkError
import com.example.githubviewerapp.ui.feature.details.composables.RepositoryDetailsBody
import com.example.githubviewerapp.ui.feature.details.composables.RepositoryDetailsItem
import com.example.githubviewerapp.ui.feature.issue.navigateToIssuesScreen
import com.example.githubviewerapp.ui.navigation.LocalNavigationProvider

@Composable
fun DetailsScreen(
    viewModel: DetailsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val navController = LocalNavigationProvider.current
    DetailsContent(state, viewModel)
    LaunchedEffect(key1 = true) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is DetailsUiEffect.NavigateToIssues -> {
                    navController.navigateToIssuesScreen(effect.owner, effect.repositoryName)
                }
            }
        }
    }

}

@Composable
fun DetailsContent(
    state: DetailsUiState,
    listener: DetailsInteractionListener,
) {
    ApplicationScaffold(textToShow = "Repository Details") {
        Loading(state = state.isLoading)
        NetworkError(state = state.isError, error = "ops, something went wrong")
        ContentVisibility(state.contentScreen()) {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                RepositoryDetailsBody(
                    repositoryOwner = state.owner,
                    repositoryName = state.repoName,
                    repositoryDescription = state.item.description,
                    ownerAvatarUrl = state.item.owner.userAvatarUrl,
                    data = state.item.createdAt,
                    language = state.item.language
                )
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(vertical = 8.dp)
                ) {
                    RepositoryDetailsItem(
                        Color.Blue,
                        R.drawable.issues,
                        "Issues",
                        state.item.openIssuesCount
                    ) {
                        listener.onClickIssues(state.owner, state.repoName)
                    }
                    RepositoryDetailsItem(
                        Color.Yellow,
                        R.drawable.star,
                        "Stars",
                        state.item.starCount
                    )
                    RepositoryDetailsItem(
                        Color.Red,
                        R.drawable.fork,
                        "Forks",
                        state.item.starCount
                    )
                    RepositoryDetailsItem(
                        Color.Blue,
                        R.drawable.contributors,
                        "Contributors",
                        state.item.subscribersCount
                    )
                    RepositoryDetailsItem(
                        Color.Magenta,
                        R.drawable.watcher,
                        "Watchers",
                        state.item.watchersCount
                    )
                }
            }
        }
    }
}