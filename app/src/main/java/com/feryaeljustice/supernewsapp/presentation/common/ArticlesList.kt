package com.feryaeljustice.supernewsapp.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.feryaeljustice.supernewsapp.domain.model.Article
import com.feryaeljustice.supernewsapp.presentation.Dimens.ExtraSmallPadding2
import com.feryaeljustice.supernewsapp.presentation.Dimens.MediumPadding1

@Composable
fun ArticlesListNoPaging(
    modifier: Modifier = Modifier,
    articles: List<Article>,
    onClick: (Article) -> Unit,
) {
    if (articles.isEmpty()) {
        EmptyScreen()
    }
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(MediumPadding1),
        contentPadding = PaddingValues(all = ExtraSmallPadding2),
    ) {
        items(
            count = articles.size,
        ) {
            articles[it].let { article ->
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
) {
    val handlePagingResult = handlePagingResult(articles)

    if (handlePagingResult) {
        if (articles.itemCount == 0) {
            Text(
                text = "No articles available",
                modifier =
                    modifier
                        .fillMaxSize()
                        .padding(MediumPadding1),
                textAlign = TextAlign.Center,
            )
        } else {
            LazyColumn(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(MediumPadding1),
                contentPadding = PaddingValues(all = ExtraSmallPadding2),
            ) {
                items(
                    count = articles.itemCount,
                ) {
                    articles[it]?.let { article ->
                        ArticleCard(article = article, onClick = { onClick(article) })
                    }
                }
            }
        }
    }
}

@Composable
fun handlePagingResult(articles: LazyPagingItems<Article>): Boolean {
    val loadState = articles.loadState
    val error =
        when {
            loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
            loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
            loadState.append is LoadState.Error -> loadState.append as LoadState.Error
            else -> null
        }

    return when {
        loadState.refresh is LoadState.Loading -> {
            ShimmerEffect()
            false
        }

        error != null -> {
            // EmptyScreen(error = error)
            false
        }

        else -> {
            true
        }
    }
}

@Composable
fun ShimmerEffect() {
    Column(verticalArrangement = Arrangement.spacedBy(MediumPadding1)) {
        repeat(10) {
            ArticleCardShimmerEffect(
                modifier = Modifier.padding(horizontal = MediumPadding1),
            )
        }
    }
}
