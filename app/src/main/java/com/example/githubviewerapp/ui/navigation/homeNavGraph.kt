package com.example.githubviewerapp.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.example.githubviewerapp.ui.feature.details.detailsRoute
import com.example.githubviewerapp.ui.feature.home.homeRoute


fun NavGraphBuilder.homeNavGraph(){
    navigation(
        startDestination = Screen.Home.route,
        route = Graph.HOME
    ){
        homeRoute()
        detailsRoute()
    }
}