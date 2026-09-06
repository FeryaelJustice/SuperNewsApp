package com.feryaeljustice.supernewsapp.presentation.common

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.feryaeljustice.supernewsapp.R
import com.feryaeljustice.supernewsapp.domain.model.Article
import com.feryaeljustice.supernewsapp.domain.model.Source
import com.feryaeljustice.supernewsapp.domain.util.DateTimeUtils
import com.feryaeljustice.supernewsapp.presentation.Dimens.ArticleCardSize
import com.feryaeljustice.supernewsapp.ui.theme.SuperNewsAppTheme

@Composable
fun ArticleCard(
    modifier: Modifier = Modifier,
    article: Article,
    onClick: () -> Unit,
) {
    val context = LocalContext.current
    val relativeTime = DateTimeUtils.formatRelativeTime(article.publishedAt)
    val sourceName = article.source?.name?.takeIf { it.isNotBlank() } ?: "SuperNews"

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Miniatura de la noticia con bordes redondeados y shimmer neutro
            Box(
                modifier = Modifier
                    .size(ArticleCardSize)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            ) {
                if (!article.urlToImage.isNullOrBlank()) {
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(article.urlToImage)
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        modifier = Modifier
                            .size(ArticleCardSize)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search_document),
                        contentDescription = null,
                        modifier = Modifier
                            .size(32.dp)
                            .align(Alignment.Center),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Columna con detalles de la noticia
            Column(
                modifier = Modifier
                    .weight(1f)
                    .height(ArticleCardSize),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Badge de la Fuente
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
                ) {
                    Text(
                        text = sourceName,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp
                        ),
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                // Titular de la noticia (2 líneas)
                Text(
                    text = article.title.orEmpty(),
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.SemiBold,
                        lineHeight = 18.sp
                    ),
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                // Fila inferior con autor (si existe) y tiempo relativo
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (!article.author.isNullOrBlank()) {
                        Text(
                            text = article.author.trim(),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.weight(1f, fill = false)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "•",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                    }

                    Icon(
                        painter = painterResource(id = R.drawable.ic_time),
                        contentDescription = null,
                        modifier = Modifier.size(11.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = relativeTime,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ArticleCardPreview() {
    SuperNewsAppTheme {
        ArticleCard(
            article = Article(
                author = "Jane Doe",
                content = "Article content snippet...",
                description = "Article short summary for preview...",
                publishedAt = "2026-03-01T14:30:00Z",
                source = Source(id = "bbc", name = "BBC News"),
                title = "Global leaders reach historic climate agreement in Geneva summit",
                url = "https://bbc.com",
                urlToImage = "",
            ),
            onClick = {}
        )
    }
}
