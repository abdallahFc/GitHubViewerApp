package com.example.githubviewerapp.ui.feature.home


import androidx.paging.PagingData
import com.example.githubviewerapp.domain.model.Repository
import com.example.githubviewerapp.domain.model.User
import com.example.githubviewerapp.ui.base.ErrorHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


data class HomeUiState(
    val isLoading: Boolean = false,
    val repositories: List<RepositoryUiModel> = emptyList(),
    val error: ErrorHandler? = null,
    val isError: Boolean = false,
    val isEmpty: Boolean = false,
    val items: Flow<PagingData<RepositoryUiModel>> = flow{},
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

fun HomeUiState.contentScreen() = !this.isLoading && !this.isError && this.repositories.isNotEmpty()

fun HomeUiState.emptyPlaceHolder() = this.isEmpty &&
        !this.isError && !this.isLoading