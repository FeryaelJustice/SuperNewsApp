package com.feryaeldev.supernewsapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import com.feryaeldev.supernewsapp.domain.usecase.AppEntryUseCases
import com.feryaeldev.supernewsapp.presentation.onboarding.OnBoardingScreen
import com.feryaeldev.supernewsapp.presentation.onboarding.OnBoardingViewModel
import com.feryaeldev.supernewsapp.presentation.onboarding.components.OnBoardingPage
import com.feryaeldev.supernewsapp.ui.theme.SuperNewsAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var appEntryUseCases: AppEntryUseCases

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        installSplashScreen()
        enableEdgeToEdge()

        lifecycleScope.launch {
            appEntryUseCases.readAppEntry().collect {
                Log.d("Test", it.toString())
            }
        }

        setContent {
            SuperNewsAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.background(color = MaterialTheme.colorScheme.background)) {
                        val viewModel: OnBoardingViewModel = hiltViewModel()
                        OnBoardingScreen(innerPadding = innerPadding, event = viewModel::onEvent)
                    }
                }
            }
        }
    }
}