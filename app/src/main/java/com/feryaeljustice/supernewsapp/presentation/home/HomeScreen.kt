package com.feryaeljustice.supernewsapp.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.feryaeljustice.supernewsapp.R
import com.feryaeljustice.supernewsapp.domain.model.Article
import com.feryaeljustice.supernewsapp.presentation.Dimens.ExtraSmallPadding
import com.feryaeljustice.supernewsapp.presentation.Dimens.MediumPadding1
import com.feryaeljustice.supernewsapp.presentation.common.ArticlesList
import com.feryaeljustice.supernewsapp.presentation.common.NewsTicker

@Composable
fun HomeScreen(
//    navigateToSearch: () -> Unit,
//    navigateToContact: () -> Unit,
    navigateToDetails: (Article) -> Unit,
) {
    val viewModel: HomeViewModel = hiltViewModel()
    // Here we decide to translate the news before showing or not
    val articles = viewModel.news.collectAsLazyPagingItems()
//  val translatedArticles = viewModel.translatedNews.collectAsLazyPagingItems()

    val titles by remember {
        derivedStateOf {
            if (articles.itemCount > 10) {
                articles.itemSnapshotList.items
                    .slice(IntRange(start = 0, endInclusive = 9))
                    .joinToString(separator = " \uD83D\uDFE5 ") { it.title.toString() }
            } else {
                ""
            }
        }
    }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(top = MediumPadding1)
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MediumPadding1),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(horizontalArrangement = Arrangement.Start) {
                Image(
                    painter = painterResource(id = R.drawable.ic_home),
                    contentDescription = null,
                    alignment = Alignment.CenterStart,
                    colorFilter = ColorFilter.tint(if (isSystemInDarkTheme()) Color.White else Color.Black),
                    modifier =
                        Modifier
                            .width(150.dp)
                            .height(30.dp),
                )

                Spacer(modifier = Modifier.width(ExtraSmallPadding))

                Text(
                    text = stringResource(id = R.string.home),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
//            Image(
//                painter = painterResource(id = R.drawable.ic_contact),
//                contentDescription = null,
//                alignment = Alignment.CenterEnd,
//                colorFilter = ColorFilter.tint(if (isSystemInDarkTheme()) Color.White else Color.Black),
//                modifier = Modifier
//                    .width(30.dp)
//                    .height(30.dp)
//                    .clickable { navigateToContact() }
//            )
        }

        Spacer(modifier = Modifier.height(MediumPadding1))

        NewsTicker(
            titles = titles,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MediumPadding1)
        )

        Spacer(modifier = Modifier.height(MediumPadding1))

        ArticlesList(
            modifier = Modifier.padding(horizontal = MediumPadding1),
            articles = articles,
            onClick = navigateToDetails,
        )
    }
}
