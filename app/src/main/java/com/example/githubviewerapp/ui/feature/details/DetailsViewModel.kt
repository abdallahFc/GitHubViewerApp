package com.example.githubviewerapp.ui.feature.details

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import com.example.githubviewerapp.domain.model.Repository
import com.example.githubviewerapp.domain.model.RepositoryDetails
import com.example.githubviewerapp.domain.repo.GithubRepository
import com.example.githubviewerapp.ui.base.BaseViewModel
import com.example.githubviewerapp.ui.feature.home.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject
@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: GithubRepository,
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
        Log.d("dfdfdf","dd ${state.value}")
    }
    fun getRepositoryDetails(){
        _state.update { it.copy(isLoading = true) }
        tryToExecute(
            {
                repository.getRepositoryDetails(_state.value.owner,_state.value.repoName)

            },
            ::onGetRepositoriesDetailsSuccess,
            ::onGetRepositoriesDetailsError
        )
    }
    private fun onGetRepositoriesDetailsSuccess(repositories:RepositoryDetails) {
        _state.update { uiState ->
            uiState.copy(
                item= repositories.toUiModel(),
                isLoading = false
            )
        }
    }

    private fun onGetRepositoriesDetailsError(error: String) {
        _state.update { it.copy(error = error, isLoading = false) }
    }

}