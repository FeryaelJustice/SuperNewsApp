package com.feryaeljustice.supernewsapp.presentation.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.feryaeljustice.supernewsapp.R
import com.feryaeljustice.supernewsapp.domain.model.Article
import com.feryaeljustice.supernewsapp.presentation.Dimens
import com.feryaeljustice.supernewsapp.presentation.common.ArticlesList
import com.feryaeljustice.supernewsapp.presentation.common.SearchBar

@Composable
fun SearchScreen(
    navigateToDetails: (Article) -> Unit,
    viewModel: SearchViewModel = viewModel()
) {
    val state = viewModel.state.value

    SearchRealScreen(
        state = state,
        navigateToDetails = navigateToDetails,
        onEvent = { viewModel.onEvent(it) }
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SearchRealScreen(
    state: SearchState,
    onEvent: (SearchEvent) -> Unit,
    navigateToDetails: (Article) -> Unit
) {
    val trendingTopics = listOf(
        "Tecnología",
        "Ciencia",
        "Economía",
        "Deportes",
        "Salud",
        "Cultura",
        "Inteligencia Artificial"
    )

    if (state.searchQuery.isEmpty()) {
        // Modo inicial ("Explorar noticias"): se mantiene en Column fija sin scroll vertical genérico
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = Dimens.MediumPadding1,
                    start = Dimens.MediumPadding1,
                    end = Dimens.MediumPadding1,
                )
        ) {
            SearchBar(
                text = state.searchQuery,
                readOnly = false,
                onValueChange = { onEvent(SearchEvent.UpdateSearchQuery(it)) },
                onSearch = { onEvent(SearchEvent.SearchNews) },
            )

            Spacer(modifier = Modifier.height(18.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = stringResource(R.string.trending_topics),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(14.dp))

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    trendingTopics.forEach { topic ->
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                            modifier = Modifier.clickable {
                                onEvent(SearchEvent.UpdateSearchQuery(topic))
                                onEvent(SearchEvent.SearchNews)
                            }
                        ) {
                            Text(
                                text = "# $topic",
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Medium),
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(48.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f),
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = stringResource(R.string.enter_search_query),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                    )
                }
            }
        }
    } else {
        // Cuando aparecen las noticias: se activa el scroll vertical genérico unificado
        // donde la barra de búsqueda y los resultados forman parte del mismo contenedor desplazable.
        state.articles?.let {
            val articles = it.collectAsLazyPagingItems()
            ArticlesList(
                modifier = Modifier.padding(horizontal = Dimens.MediumPadding1),
                articles = articles,
                onClick = navigateToDetails,
                headerContent = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = Dimens.MediumPadding1, bottom = 12.dp)
                    ) {
                        SearchBar(
                            text = state.searchQuery,
                            readOnly = false,
                            onValueChange = { query -> onEvent(SearchEvent.UpdateSearchQuery(query)) },
                            onSearch = { onEvent(SearchEvent.SearchNews) },
                        )
                    }
                }
            )
        }
    }
}
