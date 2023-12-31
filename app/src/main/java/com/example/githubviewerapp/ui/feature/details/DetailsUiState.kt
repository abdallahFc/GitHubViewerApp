package com.example.githubviewerapp.ui.feature.details

import com.example.githubviewerapp.domain.model.RepositoryDetails
import com.example.githubviewerapp.ui.base.ErrorHandler
import com.example.githubviewerapp.ui.feature.home.UserUiModel
import com.example.githubviewerapp.ui.feature.home.toUiModel
import convertDateFormat

data class DetailsUiState(
    val item: RepositoryDetailsUiModel= RepositoryDetailsUiModel(),
    val owner:String="",
    val repoName:String="",
    val isLoading: Boolean = false,
    val error: ErrorHandler? = null,
    val isError:Boolean=false
)
data class RepositoryDetailsUiModel(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val language: String = "",
    val starCount: Int = 0,
    val watchersCount: Int = 0,
    val forksCount: Int = 0,
    val openIssuesCount: Int = 0,
    val subscribersCount: Int = 0,
    val createdAt: String = "",
    val owner: UserUiModel = UserUiModel(),
)

fun RepositoryDetails.toUiModel() = RepositoryDetailsUiModel(
    id = id,
    name = name,
    description = description,
    language = language,
    starCount = starCount,
    watchersCount = watchersCount,
    forksCount = forksCount,
    openIssuesCount = openIssuesCount,
    subscribersCount = subscribersCount,
    createdAt = createdAt.convertDateFormat(),
    owner = owner.toUiModel()
)

fun DetailsUiState.contentScreen()= !this.isLoading && !this.isError

