package com.feryaeljustice.supernewsapp.presentation.mainActivity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.feryaeljustice.supernewsapp.presentation.navigation.NavGraph
import com.feryaeljustice.supernewsapp.ui.theme.SuperNewsAppTheme
import dagger.hilt.android.AndroidEntryPoint
import com.feryaeljustice.supernewsapp.ui.theme.Blue
import com.feryaeljustice.supernewsapp.ui.theme.LightRed

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        installSplashScreen().apply { setKeepOnScreenCondition { viewModel.splashCondition.value } }
        enableEdgeToEdge()

        // Set up an OnPreDrawListener to the root view.
        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    // Check whether the initial data is ready.
                    return if (viewModel.splashCondition.value) {
                        // The content is ready. Start drawing.
                        content.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    } else {
                        // The content isn't ready. Suspend.
                        false
                    }
                }
            }
        )

        // MAIN APP
        setContent {
            SuperNewsAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
                    Log.d("tags", paddingValues.toString())
                    val isSystemInDarkMode = isSystemInDarkTheme()

                    val statusBarLight = Blue.toArgb()
                    val statusBarDark = LightRed.toArgb()
                    val navigationBarLight = Blue.toArgb()
                    val navigationBarDark = LightRed.toArgb()
                    val context = LocalContext.current as ComponentActivity

                    DisposableEffect(isSystemInDarkMode) {
                        context.enableEdgeToEdge(
                            statusBarStyle = if (!isSystemInDarkMode) {
                                SystemBarStyle.light(
                                    statusBarLight,
                                    statusBarDark
                                )
                            } else {
                                SystemBarStyle.dark(
                                    statusBarDark
                                )
                            },
                            navigationBarStyle = if(!isSystemInDarkMode){
                                SystemBarStyle.light(
                                    navigationBarLight,
                                    navigationBarDark
                                )
                            } else {
                                SystemBarStyle.dark(navigationBarDark)
                            }
                        )

                        onDispose { }
                    }

                    Box(modifier = Modifier.background(color = MaterialTheme.colorScheme.background)) {
                        NavGraph(startDestination = viewModel.startDestination.value)
                    }
                }
            }
        }
    }
}