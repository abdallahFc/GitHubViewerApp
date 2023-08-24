package com.example.githubviewerapp.ui.feature.home


import com.example.githubviewerapp.domain.model.User
import com.example.githubviewerapp.domain.model.Repository


data class HomeUiState(
    val isLoading: Boolean = false,
    val userName: String = "",
    val userAvatarUrl: String = "",
    val repositories: List<RepositoryUiModel> = emptyList(),
    val error: String = ""
)
data class RepositoryUiModel(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val user : UserUiModel = UserUiModel()
)
data class UserUiModel(
    val userName: String = "",
    val userAvatarUrl: String = ""
)

fun Repository.toUiModel() = RepositoryUiModel(
    id = id,
    name = name,
    description = description,
    user = owner.toUiModel()
)
fun User.toUiModel() = UserUiModel(
    userName = name,
    userAvatarUrl = imageUrl
)
