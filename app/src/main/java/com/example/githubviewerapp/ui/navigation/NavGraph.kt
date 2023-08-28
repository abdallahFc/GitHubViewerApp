package com.example.githubviewerapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import com.example.githubviewerapp.ui.feature.details.detailsRoute
import com.example.githubviewerapp.ui.feature.home.homeRoute
import com.example.githubviewerapp.ui.feature.issue.issuesRoute

@Composable
fun MainNavGraph() {
    val navController = LocalNavigationProvider.current
    NavHost(navController = navController, startDestination = Screen.Home.route,
        ) {
        homeRoute()
        detailsRoute()
        issuesRoute()
    }
}