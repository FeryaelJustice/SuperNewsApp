package com.feryaeljustice.supernewsapp.presentation.common

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.feryaeljustice.supernewsapp.R
import com.feryaeljustice.supernewsapp.domain.model.Article
import com.feryaeljustice.supernewsapp.ui.theme.BreakingRed
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

/**
 * Cinta interactiva de Última Hora (Breaking News Ticker).
 * Muestra un distintivo llamativo en rojo y rota automáticamente los titulares
 * con animación vertical suave. Permite al usuario tocar el titular para abrir la noticia.
 */
@Composable
fun BreakingNewsTicker(
    articles: List<Article>,
    modifier: Modifier = Modifier,
    onArticleClick: (Article) -> Unit,
) {
    if (articles.isEmpty()) return

    val breakingArticles = remember(articles) { articles.take(8) }
    var currentIndex by remember { mutableIntStateOf(0) }

    // Rotación automática cada 4 segundos
    LaunchedEffect(breakingArticles.size) {
        if (breakingArticles.size > 1) {
            while (true) {
                delay(4000.milliseconds)
                currentIndex = (currentIndex + 1) % breakingArticles.size
            }
        }
    }

    // Animación de pulso para el punto rojo en vivo
    val infiniteTransition = rememberInfiniteTransition(label = "livePulse")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseAlpha"
    )

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp)),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Badge [ 🔴 ÚLTIMA HORA ]
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = BreakingRed
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(7.dp)
                            .alpha(pulseAlpha)
                            .background(Color.White, shape = CircleShape)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = stringResource(id = R.string.breaking_badge),
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.width(10.dp))

            // Titular animado e interactivo
            val currentArticle = breakingArticles.getOrNull(currentIndex)
            if (currentArticle != null) {
                AnimatedContent(
                    targetState = currentArticle,
                    transitionSpec = {
                        slideInVertically(
                            animationSpec = tween(400),
                            initialOffsetY = { it }
                        ) togetherWith slideOutVertically(
                            animationSpec = tween(400),
                            targetOffsetY = { -it }
                        )
                    },
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onArticleClick(currentArticle) },
                    label = "tickerTransition"
                ) { article ->
                    Text(
                        text = article.title.orEmpty(),
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

/**
 * Sobrecarga compatible con versiones anteriores de NewsTicker basada en String simple.
 */
@Composable
fun NewsTicker(titles: String, modifier: Modifier = Modifier) {
    if (titles.isBlank()) return

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp)),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "⚡",
                modifier = Modifier.padding(end = 8.dp),
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = titles,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
