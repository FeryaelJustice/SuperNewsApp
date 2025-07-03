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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.feryaeljustice.supernewsapp.R
import com.feryaeljustice.supernewsapp.presentation.Dimens.ArticleCardSize
import com.feryaeljustice.supernewsapp.presentation.Dimens.ExtraSmallPadding
import com.feryaeljustice.supernewsapp.presentation.Dimens.MediumPadding1
import com.feryaeljustice.supernewsapp.ui.theme.SuperNewsAppTheme

@SuppressLint("ModifierFactoryUnreferencedReceiver")
fun Modifier.shimmerEffect() =
    composed {
        val transition = rememberInfiniteTransition(label = "")
        val alpha =
            transition
                .animateFloat(
                    initialValue = 0.2f,
                    targetValue = 0.9f,
                    animationSpec =
                        infiniteRepeatable(
                            animation = tween(durationMillis = 1000),
                            repeatMode = RepeatMode.Reverse,
                        ),
                    label = "",
                ).value
        background(color = colorResource(id = R.color.shimmer).copy(alpha = alpha))
    }

@Composable
fun ArticleCardShimmerEffect(modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        Box(
            modifier =
                Modifier
                    .size(ArticleCardSize)
                    .clip(MaterialTheme.shapes.medium)
                    .shimmerEffect(),
        )

        Column(
            verticalArrangement = Arrangement.SpaceAround,
            modifier =
                Modifier
                    .padding(
                        horizontal = ExtraSmallPadding,
                    )
                    .height(ArticleCardSize),
        ) {
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(30.dp)
                        .padding(horizontal = MediumPadding1)
                        .shimmerEffect(),
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier =
                        Modifier
                            .fillMaxWidth(0.5f)
                            .height(15.dp)
                            .padding(horizontal = MediumPadding1)
                            .shimmerEffect(),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun ArticleCardShimmerEffectPreview() {
    SuperNewsAppTheme {
        ArticleCardShimmerEffect()
    }
}
