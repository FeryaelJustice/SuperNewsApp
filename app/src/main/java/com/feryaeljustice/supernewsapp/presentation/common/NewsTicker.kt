package com.feryaeljustice.supernewsapp.presentation.common

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.sp
import com.feryaeljustice.supernewsapp.R
import kotlinx.coroutines.delay

@Composable
fun NewsTicker(titles: String, modifier: Modifier = Modifier) {
    // Un ScrollState que vive sólo en memoria de la propia pantalla
    val scrollState = rememberScrollState(initial = 0)

    // Calculamos el ancho máximo una vez el layout ha medido el contenido
    var maxScroll by remember { mutableStateOf(0) }
    LaunchedEffect(titles) {
        // Delay mínimo para asegurarnos de que scrollState.maxValue ya esté bien inicializado
        delay(100)
        maxScroll = scrollState.maxValue
    }

    // Animamos el scroll en bucle infinito, **pero sin disparar eventos al ViewModel**
    LaunchedEffect(maxScroll) {
        if (maxScroll > 0) {
            while (true) {
                scrollState.scrollTo(0)
                scrollState.animateScrollTo(
                    maxScroll,
                    animationSpec = infiniteRepeatable(
                        tween(
                            durationMillis = 50_000,
                            easing = LinearEasing
                        )
                    )
                )
                // Opcional un pequeño retardo si quieres “pausa” antes de reiniciar
                delay(1_000)
            }
        }
    }

    Text(
        text = titles,
        modifier = modifier
            .horizontalScroll(scrollState, enabled = false),
        fontSize = 12.sp,
        color = colorResource(id = R.color.placeholder)
    )
}
