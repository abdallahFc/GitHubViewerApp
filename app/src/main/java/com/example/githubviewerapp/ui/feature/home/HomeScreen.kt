package com.example.githubviewerapp.ui.feature.home

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.githubviewerapp.domain.model.Repository

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    LaunchedEffect(key1 = true) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is HomeUiEffect.NavigateToRepositoryDetails -> {
                    Log.d("HomeScreen", "HomeScreen: NavigateToRepositoryDetails${effect.owner} ${effect.repositoryName}")
                }
            }
        }
    }
    Log.d("HomeScreen", "HomeScreen: $state")
    HomeContent(state, viewModel)
}

@Composable
fun HomeContent(
    state: HomeUiState,
    listener: HomeInteractionListener
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ) {
        items(state.repositories.size) { index ->
            RepositoryItem(state.repositories[index], onClick = {
                listener.onClickRepositoryItem(
                    state.repositories[index].owner.name,
                    state.repositories[index].name
                )
            })
        }
    }
}

@Composable
fun RepositoryItem(repository: Repository, onClick: () -> Unit ) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onClick()},
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = repository.name,
                style = typography.h6,
                color = MaterialTheme.colors.primary
            )
            Spacer(modifier = Modifier.padding(4.dp))
            Text(
                text = repository.description,
                style = typography.body1,
                color = MaterialTheme.colors.onBackground
            )
            Spacer(modifier = Modifier.padding(8.dp))
            Text(
                text = "Owner: ${repository.owner.name}",
                style = typography.caption,
                color = MaterialTheme.colors.onBackground
            )
        }
    }
}
