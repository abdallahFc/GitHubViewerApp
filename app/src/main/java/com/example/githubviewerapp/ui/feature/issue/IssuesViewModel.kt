package com.example.githubviewerapp.ui.feature.issue

import androidx.lifecycle.SavedStateHandle
import com.example.githubviewerapp.domain.model.Issue
import com.example.githubviewerapp.domain.repo.GithubRepository
import com.example.githubviewerapp.ui.base.BaseUiEffect
import com.example.githubviewerapp.ui.base.BaseViewModel
import com.example.githubviewerapp.ui.base.ErrorHandler
import com.example.githubviewerapp.ui.util.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class IssuesViewModel @Inject constructor(
    private val repository: GithubRepository,
    private val dispatcherProvider: DispatcherProvider,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<IssuesUiState, BaseUiEffect>(IssuesUiState()) {

    private val args = IssuesArgs(savedStateHandle)

    init {
        getAllIssues()
    }

    fun getAllIssues() {
        _state.update { it.copy(isLoading = true, isEmpty = false) }
        tryToExecute(
            {
                repository.getIssues(
                    owner = args.owner, repo = args.repoName
                )
            }, ::onGetIssuesSuccess, ::onGetIssuesError, dispatcherProvider.io
        )
    }

    private fun onGetIssuesSuccess(issues: List<Issue>) {
        if (issues.isEmpty()) {
            _state.update { it.copy(isLoading = false, isEmpty = true) }
            return
        }
        _state.update { uiState ->
            uiState.copy(
                items = issues.map { it.toUiModel() },
                isLoading = false,
                isError = false,
                error = null
            )
        }
    }

    private fun onGetIssuesError(error: ErrorHandler) {
        _state.update { it.copy(error = error, isLoading = false, isError = true, isEmpty = false) }
    }
}