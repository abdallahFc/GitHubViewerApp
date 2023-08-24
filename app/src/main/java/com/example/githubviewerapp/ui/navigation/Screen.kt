package com.example.githubviewerapp.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Details : Screen("details")
    object DetailsWithArgs : Screen("details/{owner}/{repoName}")
}
