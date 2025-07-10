package com.feryaeljustice.supernewsapp.presentation.newsDetail

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.feryaeljustice.supernewsapp.R
import com.feryaeljustice.supernewsapp.domain.model.Article
import com.feryaeljustice.supernewsapp.domain.model.Source
import com.feryaeljustice.supernewsapp.presentation.Dimens.ArticleImageHeight
import com.feryaeljustice.supernewsapp.presentation.Dimens.MediumPadding1
import com.feryaeljustice.supernewsapp.presentation.Dimens.SmallPadding1
import com.feryaeljustice.supernewsapp.presentation.newsDetail.components.DetailsTopBar
import com.feryaeljustice.supernewsapp.ui.theme.SuperNewsAppTheme
import com.feryaeljustice.supernewsapp.util.UIComponent

@Composable
fun DetailsScreen(
    article: Article,
    navigateUp: () -> Unit,
) {
    val viewModel: NewsDetailScreenViewModel = hiltViewModel()
    val context = LocalContext.current

    var isBookmarked by remember { mutableStateOf(false) }

    LaunchedEffect(article) {
        isBookmarked = viewModel.checkIfArticleIsSaved(article)
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

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        DetailsTopBar(
            onBrowsingClick = {
                Intent(Intent.ACTION_VIEW).also {
                    it.data = article.url.toUri()
                    if (it.resolveActivity(context.packageManager) != null) {
                        context.startActivity(it)
                    }
                }
            },
            onShareClick = {
                Intent(Intent.ACTION_SEND).also {
                    it.type = "text/plain"
                    it.putExtra(Intent.EXTRA_TEXT, article.url)
                    if (it.resolveActivity(context.packageManager) != null) {
                        context.startActivity(Intent.createChooser(it, null))
                    }
                }
            },
            onBookmarkClick = {
                viewModel.onEvent(NewsDetailEvent.UpsertDeleteArticle(article))
                isBookmarked = !isBookmarked
            },
            onBackClick = navigateUp,
            isBookmarked = isBookmarked,
        )

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding =
                PaddingValues(
                    start = MediumPadding1,
                    end = MediumPadding1,
                    bottom = MediumPadding1,
                ),
        ) {
            item {
                AsyncImage(
                    model =
                        ImageRequest
                            .Builder(context = context)
                            .data(article.urlToImage)
                            .build(),
                    contentDescription = null,
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(ArticleImageHeight)
                            .clip(MaterialTheme.shapes.medium),
                    contentScale = ContentScale.Crop,
                )

                Spacer(modifier = Modifier.height(MediumPadding1))

                Text(
                    text = article.title.toString(),
                    style = MaterialTheme.typography.displaySmall,
                    color =
                        colorResource(
                            id = R.color.text_title,
                        ),
                )

                Spacer(modifier = Modifier.height(SmallPadding1))

                Text(
                    text = article.content,
                    style = MaterialTheme.typography.bodyMedium,
                    color =
                        colorResource(
                            id = R.color.iconTint,
                        ),
                )
            }
        }
    }
}

@Preview
@Composable
fun DetailsScreenPreview() {
    SuperNewsAppTheme {
        DetailsScreen(
            article =
                Article(
                    author = "",
                    title = "Coinbase says Apple blocked its last app release on NFTs in Wallet ... - CryptoSaurus",
                    description = "Coinbase says Apple blocked its last app release on NFTs in Wallet ... - CryptoSaurus",
                    content = "We use cookies and data to Deliver and maintain Google services Track outages and protect against spam, fraud, and abuse Measure audience engagement and site statistics to unde… [+1131 chars]",
                    publishedAt = "2023-06-16T22:24:33Z",
                    source =
                        Source(
                            id = "",
                            name = "bbc",
                        ),
                    url = "https://consent.google.com/ml?continue=https://news.google.com/rss/articles/CBMiaWh0dHBzOi8vY3J5cHRvc2F1cnVzLnRlY2gvY29pbmJhc2Utc2F5cy1hcHBsZS1ibG9ja2VkLWl0cy1sYXN0LWFwcC1yZWxlYXNlLW9uLW5mdHMtaW4td2FsbGV0LXJldXRlcnMtY29tL9IBAA?oc%3D5&gl=FR&hl=en-US&cm=2&pc=n&src=1",
                    urlToImage = "https://media.wired.com/photos/6495d5e893ba5cd8bbdc95af/191:100/w_1280,c_limit/The-EU-Rules-Phone-Batteries-Must-Be-Replaceable-Gear-2BE6PRN.jpg",
                ),
        ) {
        }
    }
}
