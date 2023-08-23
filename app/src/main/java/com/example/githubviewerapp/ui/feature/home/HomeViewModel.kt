package com.example.githubviewerapp.ui.feature.home

import com.example.githubviewerapp.domain.model.Repository
import com.example.githubviewerapp.domain.repo.GithubRepository
import com.example.githubviewerapp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: GithubRepository
) : BaseViewModel<HomeUiState, HomeUiEffect>(HomeUiState()) , HomeInteractionListener {

    init {
        getAllRepositories()
    }

    private fun getAllRepositories() {
        _state.update { it.copy(isLoading = true) }
        tryToExecute(
            { repository.getRepositories() },
            ::onGetRepositoriesSuccess,
            ::onGetRepositoriesError
        )
    }

    private fun onGetRepositoriesSuccess(repositories: List<Repository>) {
        _state.update { it.copy(repositories = repositories, isLoading = false) }
    }

    private fun onGetRepositoriesError(error: String) {
        _state.update { it.copy(error = error, isLoading = false) }
    }

    override fun onClickRepositoryItem(owner: String, repositoryName: String) {
        effectActionExecutor(
            _effect,
            HomeUiEffect.NavigateToRepositoryDetails(owner, repositoryName)
        )
    }

}