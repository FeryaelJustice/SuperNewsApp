package com.feryaeljustice.supernewsapp.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.feryaeljustice.supernewsapp.R
import com.feryaeljustice.supernewsapp.domain.model.Article

@Composable
fun ArticlesListNoPaging(
    modifier: Modifier = Modifier,
    articles: List<Article>,
    onClick: (Article) -> Unit,
    headerContent: (@Composable () -> Unit)? = null,
    emptyMessage: String? = null,
    onEmptyActionClick: (() -> Unit)? = null,
    emptyActionText: String? = null,
) {
    if (articles.isEmpty()) {
        EmptyScreen(
            emptyMessage = emptyMessage,
            actionButtonText = emptyActionText,
            onActionClick = onEmptyActionClick,
        )
    } else {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(bottom = 24.dp),
        ) {
            if (headerContent != null) {
                item {
                    headerContent()
                }
            }

            items(count = articles.size) { index ->
                val article = articles[index]
                ArticleCard(article = article, onClick = { onClick(article) })
            }
        }
    }
}

@Composable
fun ArticlesList(
    modifier: Modifier = Modifier,
    articles: LazyPagingItems<Article>,
    onClick: (Article) -> Unit,
    headerContent: (@Composable () -> Unit)? = null,
) {
    val loadState = articles.loadState
    val refreshLoading = loadState.refresh is LoadState.Loading
    val refreshError = loadState.refresh as? LoadState.Error
        ?: loadState.append as? LoadState.Error
        ?: loadState.prepend as? LoadState.Error

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(bottom = 24.dp),
    ) {
        if (headerContent != null) {
            item {
                headerContent()
            }
        }

        when {
            refreshLoading -> {
                item {
                    ShimmerEffect()
                }
            }

            refreshError != null -> {
                item {
                    EmptyScreen(
                        error = refreshError,
                        onActionClick = { articles.retry() }
                    )
                }
            }

            articles.itemCount == 0 -> {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(R.string.noContent),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }

            else -> {
                items(count = articles.itemCount) { index ->
                    articles[index]?.let { article ->
                        ArticleCard(article = article, onClick = { onClick(article) })
                    }
                }
            }
        }
    }
}
