package com.feryaeljustice.supernewsapp.presentation.home

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.feryaeljustice.supernewsapp.R
import com.feryaeljustice.supernewsapp.domain.model.Article
import com.feryaeljustice.supernewsapp.domain.util.DateTimeUtils
import com.feryaeljustice.supernewsapp.presentation.Dimens.MediumPadding1
import com.feryaeljustice.supernewsapp.presentation.common.ArticlesList
import com.feryaeljustice.supernewsapp.presentation.common.BreakingNewsTicker
import com.feryaeljustice.supernewsapp.presentation.home.components.FeaturedNewsCarousel

@Composable
fun HomeScreen(
    navigateToDetails: (Article) -> Unit,
    viewModel: HomeViewModel = viewModel()
) {
    val articles = viewModel.news.collectAsLazyPagingItems()

    // Extraer una instantánea de artículos disponibles para el carrusel y ticker
    val snapshotArticles by remember(articles.itemCount) {
        derivedStateOf {
            articles.itemSnapshotList.items.filter { !it.title.isNullOrBlank() }
        }
    }

    val formattedDate = remember { DateTimeUtils.getFormattedCurrentDate() }

    // ArticlesList ahora contiene la cabecera, ticker y carrusel dentro de su LazyColumn,
    // permitiendo un scroll vertical unificado de toda la pantalla tanto en móviles como en tablets.
    ArticlesList(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = MediumPadding1),
        articles = articles,
        onClick = navigateToDetails,
        headerContent = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.height(14.dp))

                // Cabecera de Marca Editorial
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Super",
                                style = MaterialTheme.typography.headlineLarge.copy(
                                    fontWeight = FontWeight.Black,
                                    fontSize = 26.sp,
                                    letterSpacing = (-0.5).sp
                               ),
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Text(
                                text = "News",
                                style = MaterialTheme.typography.headlineLarge.copy(
                                    fontWeight = FontWeight.Black,
                                    fontSize = 26.sp,
                                    letterSpacing = (-0.5).sp
                                ),
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.padding(2.dp))
                            Box(
                                modifier = Modifier
                                    .size(7.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.primary)
                            )
                        }

                        if (formattedDate.isNotBlank()) {
                            Text(
                                text = formattedDate,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Cinta interactiva de Última Hora (Breaking News Ticker)
                if (snapshotArticles.isNotEmpty()) {
                    BreakingNewsTicker(
                        articles = snapshotArticles,
                        onArticleClick = navigateToDetails
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Carrusel Hero Destacado (Featured News Slider)
                if (snapshotArticles.isNotEmpty()) {
                    FeaturedNewsCarousel(
                        articles = snapshotArticles,
                        onArticleClick = navigateToDetails
                    )
                    Spacer(modifier = Modifier.height(18.dp))
                }

                // Título de Sección "Últimas Noticias"
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.latest_news),
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
    )
}
