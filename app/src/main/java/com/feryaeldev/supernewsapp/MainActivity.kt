package com.feryaeldev.supernewsapp

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
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import com.feryaeldev.supernewsapp.domain.usecase.AppEntryUseCases
import com.feryaeldev.supernewsapp.presentation.navigation.NavGraph
import com.feryaeldev.supernewsapp.presentation.onboarding.OnBoardingScreen
import com.feryaeldev.supernewsapp.presentation.onboarding.OnBoardingViewModel
import com.feryaeldev.supernewsapp.presentation.onboarding.components.OnBoardingPage
import com.feryaeldev.supernewsapp.ui.theme.SuperNewsAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        installSplashScreen().apply { setKeepOnScreenCondition { viewModel.splashCondition } }
        enableEdgeToEdge()

        setContent {
            SuperNewsAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {

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