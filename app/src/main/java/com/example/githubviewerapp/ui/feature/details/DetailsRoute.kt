package com.example.githubviewerapp.ui.feature.details

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.githubviewerapp.ui.navigation.Screen

private val ROUTE = Screen.Details.route

fun NavController.navigateToDetailsScreen(owner: String, repoName: String) {
    navigate("$ROUTE/${owner}/${repoName}")
}

fun NavGraphBuilder.detailsRoute() {
    composable(
        route = "$ROUTE/{${DetailsArgs.OWNER}}/{${DetailsArgs.REPO_NAME}}",
        arguments = listOf(
            navArgument(name = DetailsArgs.OWNER) {
                NavType.StringType
            },
            navArgument(name = DetailsArgs.REPO_NAME) {
                NavType.StringType
            }
        )
    ) {
        DetailsScreen()
    }
}

class DetailsArgs(savedStateHandle: SavedStateHandle) {
    val owner: String = checkNotNull(savedStateHandle[OWNER])
    val repoName: String = checkNotNull(savedStateHandle[REPO_NAME])
    companion object {
        const val OWNER = "owner"
        const val REPO_NAME = "repoName"
    }
}
