package com.example.githubviewerapp.ui.feature.details

import androidx.lifecycle.SavedStateHandle
import com.example.githubviewerapp.domain.model.RepositoryDetails
import com.example.githubviewerapp.domain.repo.GithubRepository
import com.example.githubviewerapp.ui.base.BaseViewModel
import com.example.githubviewerapp.ui.base.ErrorHandler
import com.example.githubviewerapp.ui.util.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: GithubRepository,
    private val dispatcherProvider: DispatcherProvider,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<DetailsUiState, DetailsUiEffect>(DetailsUiState()), DetailsInteractionListener {
    private val args = DetailsArgs(savedStateHandle)
    init {
        _state.update {
            it.copy(
                owner =args.owner,
                repoName = args.repoName
            )
        }
        getRepositoryDetails()
    }
    fun getRepositoryDetails(){
        _state.update { it.copy(isLoading = true) }
        tryToExecute(
            {
                repository.getRepositoryDetails(_state.value.owner,_state.value.repoName)
            },
            ::onGetRepositoriesDetailsSuccess,
            ::onGetRepositoriesDetailsError,
            dispatcherProvider.io
        )
    }
    private fun onGetRepositoriesDetailsSuccess(repositories:RepositoryDetails) {
        _state.update { uiState ->
            uiState.copy(
                item = repositories.toUiModel(),
                isLoading = false,
                error = null,
                isError = false
            )
        }
    }

    private fun onGetRepositoriesDetailsError(error: ErrorHandler) {
        _state.update {
            it.copy(
                error = error, isLoading = false,
                isError = true
            )
        }
    }

    override fun onClickIssues(owner: String, repositoryName: String) {
        effectActionExecutor(
            DetailsUiEffect.NavigateToIssues(owner, repositoryName)
        )
    }

}