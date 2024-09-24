/*
 * Copyright (c) 2024. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.feryaeldev.supernewsapp.presentation.mainActivity

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.feryaeldev.supernewsapp.presentation.navigation.NavGraph
import com.feryaeldev.supernewsapp.ui.theme.SuperNewsAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        installSplashScreen().apply { setKeepOnScreenCondition { viewModel.splashCondition } }
        enableEdgeToEdge()

        setContent {
            SuperNewsAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
                    Log.d("tags", paddingValues.toString())
                    val isSystemInDarkMode = isSystemInDarkTheme()

                    SideEffect {
                        val windowInsetsController =
                            WindowCompat.getInsetsController(window, window.decorView)
                        windowInsetsController.isAppearanceLightStatusBars = !isSystemInDarkMode
                        window.statusBarColor = Color.Red.toArgb()
                    }

                    Box(modifier = Modifier.background(color = MaterialTheme.colorScheme.background)) {
                        val startDestination = viewModel.startDestination
                        NavGraph(startDestination = startDestination)
                    }
                }
            }
        }
    }
}