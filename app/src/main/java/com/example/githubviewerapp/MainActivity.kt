package com.example.githubviewerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.githubviewerapp.ui.feature.home.HomeScreen
import com.example.githubviewerapp.ui.navigation.LocalNavigationProvider
import com.example.githubviewerapp.ui.navigation.MainNavGraph
import com.example.githubviewerapp.ui.theme.GitHubViewerAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CompositionLocalProvider(LocalNavigationProvider provides rememberNavController()) {
                GitHubViewerAppTheme {
                    Box(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .fillMaxSize()
                    )
                    {
                        MainNavGraph()
                    }
                }
            }
        }
    }
}
