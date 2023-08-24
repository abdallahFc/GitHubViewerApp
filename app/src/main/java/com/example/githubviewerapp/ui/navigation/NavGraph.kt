package com.example.githubviewerapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost

@Composable
fun MainNavGraph() {
    val navController = LocalNavigationProvider.current
    NavHost(navController = navController, startDestination = Graph.HOME) {
        homeNavGraph()
    }
}
object Graph {
    const val HOME = "home_graph"
}