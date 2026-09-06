package com.feryaeljustice.supernewsapp.presentation.newsDetail

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.feryaeljustice.supernewsapp.R
import com.feryaeljustice.supernewsapp.domain.model.Article
import com.feryaeljustice.supernewsapp.domain.util.DateTimeUtils
import com.feryaeljustice.supernewsapp.domain.util.removeTrailingCharsIndicator
import com.feryaeljustice.supernewsapp.presentation.Dimens.ArticleImageHeight
import com.feryaeljustice.supernewsapp.presentation.Dimens.MediumPadding1
import com.feryaeljustice.supernewsapp.presentation.newsDetail.components.DetailsTopBar
import com.feryaeljustice.supernewsapp.util.UIComponent

@Composable
fun DetailsScreen(
    article: Article,
    navigateUp: () -> Unit,
    viewModel: NewsDetailScreenViewModel = viewModel()
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()

    val relativeDate = remember(article.publishedAt) {
        DateTimeUtils.formatRelativeTime(article.publishedAt)
    }

    val readingTime = remember(article.description, article.content) {
        DateTimeUtils.calculateReadingTime(article.description, article.content)
    }

    val sourceName = article.source?.name?.takeIf { it.isNotBlank() } ?: "SuperNews"

    LaunchedEffect(article) {
        viewModel.setArticle(article)
    }

    LaunchedEffect(key1 = viewModel.sideEffect) {
        viewModel.sideEffect?.let { sE ->
            when (sE) {
                is UIComponent.Toast -> {
                    Toast.makeText(context, sE.message, Toast.LENGTH_SHORT).show()
                    viewModel.onEvent(NewsDetailEvent.RemoveSideEffect)
                }
                else -> Unit
            }
        }
    }

    val openArticleInBrowser = {
        try {
            val intent = Intent(Intent.ACTION_VIEW, article.url.toUri())
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "No se pudo abrir el enlace", Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        DetailsTopBar(
            onBrowsingClick = openArticleInBrowser,
            onShareClick = {
                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, "${article.title}\n\n${article.url}")
                }
                try {
                    context.startActivity(Intent.createChooser(intent, null))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            },
            onBookmarkClick = {
                viewModel.onEvent(NewsDetailEvent.UpsertDeleteArticle(article))
            },
            onBackClick = navigateUp,
            isBookmarked = state.isSaved,
        )

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(
                start = MediumPadding1,
                end = MediumPadding1,
                bottom = 32.dp,
            ),
        ) {
            item {
                // Imagen de cabecera con bordes redondeados
                if (!article.urlToImage.isNullOrBlank()) {
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(article.urlToImage)
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(ArticleImageHeight)
                            .clip(RoundedCornerShape(18.dp)),
                        contentScale = ContentScale.Crop,
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Fila de metadatos: Fuente, Tiempo de lectura y Fecha
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.7f)
                    ) {
                        Text(
                            text = sourceName,
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_time),
                            contentDescription = null,
                            modifier = Modifier.size(13.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "$readingTime • $relativeDate",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Titular del artículo
                Text(
                    text = article.title.orEmpty(),
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        lineHeight = 28.sp
                    ),
                    color = MaterialTheme.colorScheme.onBackground,
                )

                // Autor si existe
                if (!article.author.isNullOrBlank()) {
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = stringResource(R.string.author_text, article.author.trim()),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Entradilla editorial (article.description)
                val descriptionText = article.description?.trim().orEmpty()
                if (descriptionText.isNotBlank()) {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = descriptionText,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.Medium,
                                fontStyle = FontStyle.Italic,
                                lineHeight = 24.sp
                            ),
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(14.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Cuerpo del artículo procesado
                val cleanedContent = (article.content ?: "").removeTrailingCharsIndicator().trim()
                if (cleanedContent.isNotBlank()) {
                    Text(
                        text = cleanedContent,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            lineHeight = 26.sp,
                            fontWeight = FontWeight.Normal
                        ),
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.9f),
                    )
                } else if (descriptionText.isBlank()) {
                    Text(
                        text = stringResource(R.string.noContent),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }

                Spacer(modifier = Modifier.height(28.dp))

                // Botón destacado de acción para leer el artículo original completo
                Button(
                    onClick = openArticleInBrowser,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_network),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = stringResource(R.string.read_full_story, sourceName),
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                    )
                }
            }
        }
    }
}