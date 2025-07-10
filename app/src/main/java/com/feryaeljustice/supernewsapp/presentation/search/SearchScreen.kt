package com.feryaeljustice.supernewsapp.presentation.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.feryaeljustice.supernewsapp.R
import com.feryaeljustice.supernewsapp.domain.model.Article
import com.feryaeljustice.supernewsapp.presentation.Dimens
import com.feryaeljustice.supernewsapp.presentation.common.ArticlesList
import com.feryaeljustice.supernewsapp.presentation.common.SearchBar

@Composable
fun SearchScreen(
    navigateToDetails: (Article) -> Unit,
) {
    val viewModel: SearchViewModel = hiltViewModel()
    val state = viewModel.state.value

    Column(
        modifier =
            Modifier
                .padding(
                    top = Dimens.MediumPadding1,
                    start = Dimens.MediumPadding1,
                    end = Dimens.MediumPadding1,
                )
    ) {
        SearchBar(
            text = state.searchQuery,
            readOnly = false,
            onValueChange = { viewModel.onEvent(SearchEvent.UpdateSearchQuery(it)) },
            onSearch = { viewModel.onEvent(SearchEvent.SearchNews) },
        )

        Spacer(modifier = Modifier.height(Dimens.MediumPadding1))

        if (state.searchQuery.isEmpty()) {
            Text(
                text = stringResource(R.string.enter_search_query),
                modifier = Modifier.fillMaxSize(),
                textAlign = TextAlign.Center,
            )
        } else {
            state.articles?.let {
                val articles = it.collectAsLazyPagingItems()
                ArticlesList(articles = articles, onClick = navigateToDetails)
            }
        }
    }
}
