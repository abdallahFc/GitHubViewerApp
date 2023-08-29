package com.example.githubviewerapp.ui.feature.issue

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.githubviewerapp.ui.composables.ApplicationScaffold
import com.example.githubviewerapp.ui.composables.ContentVisibility
import com.example.githubviewerapp.ui.composables.NetworkError
import com.example.githubviewerapp.ui.composables.ShimmerLoading
import com.example.githubviewerapp.ui.feature.home.emptyPlaceHolder
import com.example.githubviewerapp.ui.feature.issue.composables.IssueItem

@Composable
fun IssuesScreen(
    viewModel: IssuesViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    IssuesContent(state)
}
@Composable
fun IssuesContent(
    state: IssuesUiState
) {
    ApplicationScaffold(textToShow = "Issues") {
        ShimmerLoading(state = state.isLoading)
        NetworkError(state = state.emptyPlaceHolder(), error = "No Internet found")
        NetworkError(state = state.isError, error = "ops, something went wrong")
        ContentVisibility(state = state.contentScreen()) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(vertical = 16.dp)

            ) {
                items(state.items.size) { index ->
                    val item = state.items[index]
                    IssueItem(
                        title = item.title,
                        state = item.state,
                        repositoryOwner = item.user.userName,
                        ownerAvatarUrl = item.user.userAvatarUrl,
                        date = item.createdAt
                    )
                }
            }
        }
    }
}