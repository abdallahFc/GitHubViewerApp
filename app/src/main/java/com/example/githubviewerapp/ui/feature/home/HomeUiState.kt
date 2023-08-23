package com.example.githubviewerapp.ui.feature.home
import com.example.githubviewerapp.domain.model.Repository

data class HomeUiState(
    val isLoading: Boolean = false,
    val userName: String = "",
    val userAvatarUrl: String = "",
    val repositories: List<Repository> = emptyList(),
    val error: String = ""
)

