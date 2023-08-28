package com.example.githubviewerapp.ui.feature.home


import com.example.githubviewerapp.domain.model.Repository
import com.example.githubviewerapp.domain.repo.GithubRepository
import com.example.githubviewerapp.ui.base.BaseViewModel
import com.example.githubviewerapp.ui.base.ErrorHandler
import com.example.githubviewerapp.ui.util.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: GithubRepository,
    private val dispatcherProvider: DispatcherProvider
) : BaseViewModel<HomeUiState, HomeUiEffect>(HomeUiState()) , HomeInteractionListener {

    init {
        getAllRepositories()
    }

    fun getAllRepositories() {
        _state.update { it.copy(isLoading = true, isEmpty = false) }
        tryToExecute(
            { repository.getRepositories() },
            ::onGetRepositoriesSuccess,
            ::onGetRepositoriesError,
            dispatcherProvider.io
        )
    }

    private fun onGetRepositoriesSuccess(repositories: List<Repository>) {
        if (repositories.isEmpty()) {
            _state.update { it.copy(isLoading = false, isEmpty = true) }
            return
        }
        _state.update { uiState ->
            uiState.copy(
                repositories = repositories.map { it.toUiModel() },
                isLoading = false,
                isError = false,
                error = null,
                isEmpty = false
            )
        }
    }

    private fun onGetRepositoriesError(error: ErrorHandler) {
        _state.update { it.copy(error = error, isLoading = false, isError = true, isEmpty = false) }
    }

    override fun onClickRepositoryItem(owner: String, repositoryName: String) {
        effectActionExecutor(
            HomeUiEffect.NavigateToRepositoryDetails(owner, repositoryName)
        )
    }
}