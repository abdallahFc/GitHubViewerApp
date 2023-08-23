package com.example.githubviewerapp.ui.feature.home

import com.example.githubviewerapp.ui.base.BaseUiEffect

sealed class HomeUiEffect: BaseUiEffect {
    data class NavigateToRepositoryDetails(val owner: String, val repositoryName: String) : HomeUiEffect()
}
