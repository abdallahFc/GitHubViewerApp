package com.example.githubviewerapp.ui.feature.home

interface HomeInteractionListener {
    fun onClickRepositoryItem(owner: String, repositoryName: String)
}