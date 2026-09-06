package com.feryaeljustice.supernewsapp.presentation.bookmark

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.feryaeljustice.supernewsapp.R
import com.feryaeljustice.supernewsapp.domain.model.Article
import com.feryaeljustice.supernewsapp.presentation.Dimens.MediumPadding1
import com.feryaeljustice.supernewsapp.presentation.common.ArticlesListNoPaging

@Composable
fun BookmarkScreen(
    navigateToDetails: (Article) -> Unit,
    navigateToHome: (() -> Unit)? = null,
    viewModel: BookmarkViewModel = viewModel()
) {
    val state = viewModel.state.value

    BookmarkRealScreen(
        state = state,
        navigateToDetails = navigateToDetails,
        navigateToHome = navigateToHome,
    )
}

@Composable
fun BookmarkRealScreen(
    state: BookmarkState,
    navigateToDetails: (Article) -> Unit,
    navigateToHome: (() -> Unit)? = null,
) {
    val bookmarkText = stringResource(R.string.bookmark)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(top = MediumPadding1, start = MediumPadding1, end = MediumPadding1),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = bookmarkText,
                style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onBackground,
            )

            if (state.articles.isNotEmpty()) {
                Text(
                    text = "${state.articles.size} guardados",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        ArticlesListNoPaging(
            articles = state.articles,
            onClick = navigateToDetails,
            emptyMessage = stringResource(R.string.notSavedNews),
            emptyActionText = stringResource(R.string.explore_news),
            onEmptyActionClick = navigateToHome,
        )
    }
}
