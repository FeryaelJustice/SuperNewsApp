package com.feryaeljustice.supernewsapp.presentation.mainActivity

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.feryaeljustice.supernewsapp.presentation.navigation.NavGraph
import com.feryaeljustice.supernewsapp.ui.adaptive.LocalDeviceType
import com.feryaeljustice.supernewsapp.ui.adaptive.getDeviceType
import com.feryaeljustice.supernewsapp.ui.theme.SuperNewsAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen().apply { setKeepOnScreenCondition { viewModel.splashCondition.value } }

        // MAIN APP
        setContent {
            SuperNewsAppTheme {
                // val view = LocalView.current
                /* val useDarkIcons = !isSystemInDarkTheme()

                 SideEffect {
                     WindowCompat.getInsetsController(window, view).apply {
                         isAppearanceLightStatusBars = useDarkIcons
                         isAppearanceLightNavigationBars = useDarkIcons
                     }
                 }*/

                val windowAdaptiveInfo = currentWindowAdaptiveInfo()
                val deviceType by remember {
                    derivedStateOf {
                        getDeviceType(windowAdaptiveInfo = windowAdaptiveInfo)
                    }
                }

                val startRoute by viewModel.startDestination.collectAsStateWithLifecycle()

                Scaffold { paddingValues ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .windowInsetsPadding(WindowInsets.safeDrawing)
                            .background(color = MaterialTheme.colorScheme.background)
                    ) {
                        Log.i("MainActivity paddingValues", paddingValues.toString())
                        CompositionLocalProvider(LocalDeviceType provides deviceType) {
                            // Mientras startRoute sea null (restando milisegundos de lectura de disco), no pintamos nada.
                            // La Splash Screen cubre este espacio de tiempo.
                            startRoute?.let { route ->
                                NavGraph(startDestination = route)
                            }
                        }
                    }
                }
            }
        }
    }
}
