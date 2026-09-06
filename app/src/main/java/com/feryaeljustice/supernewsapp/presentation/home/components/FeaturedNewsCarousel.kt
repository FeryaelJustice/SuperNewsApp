package com.feryaeljustice.supernewsapp.presentation.home.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.feryaeljustice.supernewsapp.R
import com.feryaeljustice.supernewsapp.domain.model.Article
import com.feryaeljustice.supernewsapp.domain.util.DateTimeUtils
import com.feryaeljustice.supernewsapp.presentation.common.shimmerEffect
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun FeaturedNewsCarousel(
    articles: List<Article>,
    modifier: Modifier = Modifier,
    onArticleClick: (Article) -> Unit,
) {
    if (articles.isEmpty()) return

    // Limitar a los 5 artículos más destacados
    val featuredArticles = articles.take(5)
    val pagerState = rememberPagerState(initialPage = 0) { featuredArticles.size }
    val isDragged by pagerState.interactionSource.collectIsDraggedAsState()

    // Desplazamiento automático cada 4.5 segundos si el usuario no está interactuando
    LaunchedEffect(isDragged, pagerState.pageCount) {
        if (!isDragged && pagerState.pageCount > 1) {
            while (true) {
                delay(4500.milliseconds)
                val nextPage = (pagerState.currentPage + 1) % pagerState.pageCount
                pagerState.animateScrollToPage(nextPage)
            }
        }
    }

    Column(modifier = modifier.fillMaxWidth()) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(210.dp)
        ) { page ->
            val article = featuredArticles[page]
            FeaturedArticleCard(
                article = article,
                onClick = { onArticleClick(article) }
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Indicador animado de páginas (puntos / píldoras)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(featuredArticles.size) { index ->
                val isSelected = pagerState.currentPage == index
                val width by animateDpAsState(
                    targetValue = if (isSelected) 22.dp else 7.dp,
                    label = "indicatorWidth"
                )
                val color = if (isSelected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.surfaceVariant
                }

                Box(
                    modifier = Modifier
                        .padding(horizontal = 3.dp)
                        .height(6.dp)
                        .width(width)
                        .clip(CircleShape)
                        .background(color)
                )
            }
        }
    }
}

@Composable
fun FeaturedArticleCard(
    article: Article,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val context = LocalContext.current
    val relativeTime = DateTimeUtils.formatRelativeTime(article.publishedAt)
    val sourceName = article.source?.name?.takeIf { it.isNotBlank() } ?: "SuperNews"

    Card(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 4.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Imagen de fondo con recorte y fallback
            if (!article.urlToImage.isNullOrBlank()) {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(article.urlToImage)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .shimmerEffect()
                )
            }

            // Scrim / Gradiente vertical oscuro para asegurar legibilidad en toda fotografía
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.4f),
                                Color.Black.copy(alpha = 0.88f)
                            ),
                            startY = 60f
                        )
                    )
            )

            // Badge de fuente en la esquina superior izquierda
            Surface(
                modifier = Modifier
                    .padding(14.dp)
                    .align(Alignment.TopStart),
                shape = RoundedCornerShape(50),
                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.90f),
                tonalElevation = 2.dp
            ) {
                Text(
                    text = sourceName,
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                )
            }

            // Titular y metadatos en la parte inferior
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            ) {
                Text(
                    text = article.title.orEmpty(),
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        lineHeight = 22.sp
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_time),
                        contentDescription = null,
                        tint = Color.White.copy(alpha = 0.8f),
                        modifier = Modifier.size(13.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = relativeTime,
                        style = MaterialTheme.typography.labelSmall.copy(color = Color.White.copy(alpha = 0.85f))
                    )
                }
            }
        }
    }
}
