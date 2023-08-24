package com.example.githubviewerapp.ui.feature.details

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.githubviewerapp.ui.navigation.LocalNavigationProvider

@Composable
fun DetailsScreen(
    viewModel: DetailsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val navController = LocalNavigationProvider.current
    LaunchedEffect(key1 =state){
        Log.d("ddfdf","dsdsd $state")

    }

}