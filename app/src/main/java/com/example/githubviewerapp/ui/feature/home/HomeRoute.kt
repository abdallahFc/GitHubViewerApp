package com.example.githubviewerapp.ui.feature.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.githubviewerapp.ui.navigation.Screen

private val ROUTE= Screen.Home.route

fun NavGraphBuilder.homeRoute() {
    composable(ROUTE) { HomeScreen() }
}