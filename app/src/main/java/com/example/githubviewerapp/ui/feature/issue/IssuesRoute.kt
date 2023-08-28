package com.example.githubviewerapp.ui.feature.issue

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.githubviewerapp.ui.navigation.Screen

private val ROUTE = Screen.Issues.route

fun NavController.navigateToIssuesScreen(owner: String, repoName: String) {
    navigate("$ROUTE/${owner}/${repoName}")
}

fun NavGraphBuilder.issuesRoute() {
    composable(
        route = "$ROUTE/{${IssuesArgs.OWNER}}/{${IssuesArgs.REPO_NAME}}",
        arguments = listOf(
            navArgument(name = IssuesArgs.OWNER) {
                NavType.StringType
            },
            navArgument(name = IssuesArgs.REPO_NAME) {
                NavType.StringType
            }
        )
    ) {
        IssuesScreen()
    }
}

class IssuesArgs(savedStateHandle: SavedStateHandle) {
    val owner: String = checkNotNull(savedStateHandle[OWNER])
    val repoName: String = checkNotNull(savedStateHandle[REPO_NAME])
    companion object {
        const val OWNER = "owner"
        const val REPO_NAME = "repoName"
    }
}

