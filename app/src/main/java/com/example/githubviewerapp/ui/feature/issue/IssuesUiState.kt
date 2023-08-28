package com.example.githubviewerapp.ui.feature.issue

import com.example.githubviewerapp.domain.model.Issue
import com.example.githubviewerapp.ui.base.ErrorHandler
import com.example.githubviewerapp.ui.feature.home.UserUiModel
import com.example.githubviewerapp.ui.feature.home.toUiModel
import convertDateFormat

data class IssuesUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val error: ErrorHandler? = null,
    val items: List<IssueUiModel> = emptyList(),
    val isEmpty: Boolean = false
)
data class IssueUiModel(
    val id: String,
    val title: String,
    val state: String,
    val createdAt: String,
    val user: UserUiModel
)
fun Issue.toUiModel() = IssueUiModel(
    id = id,
    title = title,
    state = state,
    createdAt = createdAt.convertDateFormat(),
    user = user.toUiModel()
)
fun IssuesUiState.contentScreen() = !this.isLoading && !this.isError
fun IssuesUiState.emptyPlaceHolder() = this.isEmpty && !this.isError && !this.isLoading