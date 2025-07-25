package com.feryaeljustice.supernewsapp.presentation.bookmark

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.feryaeljustice.supernewsapp.R
import com.feryaeljustice.supernewsapp.domain.model.Article
import com.feryaeljustice.supernewsapp.presentation.Dimens.MediumPadding1
import com.feryaeljustice.supernewsapp.presentation.common.ArticlesListNoPaging

@Composable
fun BookmarkScreen(
    navigateToDetails: (Article) -> Unit,
) {
    val viewModel: BookmarkViewModel = hiltViewModel()
    val state = viewModel.state.value
    val bookmarkText = stringResource(R.string.bookmark)

    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(top = MediumPadding1, start = MediumPadding1, end = MediumPadding1),
    ) {
        Text(
            text = bookmarkText,
            style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Bold),
            color =
                colorResource(
                    id = R.color.text_title,
                ),
        )

        Spacer(modifier = Modifier.height(MediumPadding1))

        ArticlesListNoPaging(
            articles = state.articles,
            onClick = navigateToDetails,
        )
    }
}
