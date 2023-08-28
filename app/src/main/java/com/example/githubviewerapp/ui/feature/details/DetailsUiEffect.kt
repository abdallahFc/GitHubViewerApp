package com.example.githubviewerapp.ui.feature.details

import com.example.githubviewerapp.ui.base.BaseUiEffect

sealed class DetailsUiEffect :BaseUiEffect {
    data class NavigateToIssues(val owner: String, val repositoryName: String) : DetailsUiEffect()

}
