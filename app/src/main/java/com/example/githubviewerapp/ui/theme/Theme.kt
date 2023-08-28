package com.example.githubviewerapp.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorScheme = darkColorScheme(
    primary = DarkPrimaryTextColor,
    secondary = DarkSecondaryTextColor,
    tertiary = Pink80,
    background = DarkBackgroundColor,
    surface = DarkCardColor,
    error = Color.Red,
    onPrimary = Color.White,
    onSecondary = DarkSmallTextColor,
    onBackground = DarkPrimaryTextColor,
    onSurface = DarkPrimaryTextColor,
    onError = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = LightPrimaryTextColor,
    secondary = LightSecondaryTextColor,
    tertiary = Pink40,
    background = LightBackgroundColor,
    surface = LightCardColor,
    error = Color.Red,
    onPrimary = Color.Black,
    onSecondary = LightSmallTextColor,
    onBackground = LightPrimaryTextColor,
    onSurface = LightPrimaryTextColor,
    onError = Color.White
)

@Composable
fun GitHubViewerAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    useDarkIcons: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val systemUiController = rememberSystemUiController()

    DisposableEffect(systemUiController, useDarkIcons) {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = useDarkIcons,
            isNavigationBarContrastEnforced = false
        )

        onDispose {}
    }
    val statusBarColor = if (darkTheme) DarkBackgroundColor else LightBackgroundColor
    val navigationBarColor = if (darkTheme) DarkBackgroundColor else LightBackgroundColor

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = statusBarColor.toArgb()
            window.navigationBarColor = navigationBarColor.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = !darkTheme
        }
    }
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}