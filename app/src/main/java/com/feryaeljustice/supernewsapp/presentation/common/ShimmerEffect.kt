package com.feryaeljustice.supernewsapp.presentation.common

import android.annotation.SuppressLint
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.feryaeljustice.supernewsapp.presentation.Dimens.ArticleCardSize
import com.feryaeljustice.supernewsapp.presentation.Dimens.MediumPadding1
import com.feryaeljustice.supernewsapp.ui.theme.SuperNewsAppTheme

@SuppressLint("ModifierFactoryUnreferencedReceiver")
fun Modifier.shimmerEffect(): Modifier =
    composed {
        val transition = rememberInfiniteTransition(label = "shimmerTransition")
        val alpha =
            transition
                .animateFloat(
                    initialValue = 0.25f,
                    targetValue = 0.85f,
                    animationSpec =
                        infiniteRepeatable(
                            animation = tween(durationMillis = 900),
                            repeatMode = RepeatMode.Reverse,
                        ),
                    label = "shimmerAlpha",
                ).value
        background(color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = alpha))
    }

@Composable
fun ArticleCardShimmerEffect(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Placeholder de imagen
            Box(
                modifier = Modifier
                    .size(ArticleCardSize)
                    .clip(RoundedCornerShape(12.dp))
                    .shimmerEffect()
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Columnas de texto shimmer
            Column(
                modifier = Modifier
                    .weight(1f)
                    .height(ArticleCardSize),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Badge shimmer
                Box(
                    modifier = Modifier
                        .width(70.dp)
                        .height(16.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .shimmerEffect()
                )

                // Titular shimmer (dos líneas)
                Column {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.95f)
                            .height(14.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .shimmerEffect()
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.65f)
                            .height(14.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .shimmerEffect()
                    )
                }

                // Fila inferior de tiempo shimmer
                Box(
                    modifier = Modifier
                        .width(90.dp)
                        .height(12.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .shimmerEffect()
                )
            }
        }
    }
}

@Composable
fun ShimmerEffect(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        repeat(6) {
            ArticleCardShimmerEffect()
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun ArticleCardShimmerEffectPreview() {
    SuperNewsAppTheme {
        ArticleCardShimmerEffect(modifier = Modifier.padding(MediumPadding1))
    }
}
