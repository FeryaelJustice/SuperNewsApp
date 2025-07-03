package com.feryaeljustice.supernewsapp.presentation.common

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.feryaeljustice.supernewsapp.R
import com.feryaeljustice.supernewsapp.domain.model.Article
import com.feryaeljustice.supernewsapp.domain.model.Source
import com.feryaeljustice.supernewsapp.presentation.Dimens.ArticleCardSize
import com.feryaeljustice.supernewsapp.presentation.Dimens.ExtraSmallPadding
import com.feryaeljustice.supernewsapp.presentation.Dimens.ExtraSmallPadding2
import com.feryaeljustice.supernewsapp.presentation.Dimens.SmallIconSize
import com.feryaeljustice.supernewsapp.ui.theme.SuperNewsAppTheme

@Composable
fun ArticleCard(
    modifier: Modifier = Modifier,
    article: Article,
    onClick: (() -> Unit),
) {
    val context = LocalContext.current
    Row(
        modifier =
            modifier.clickable {
                onClick()
            },
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context = context).data(article.urlToImage).build(),
            contentDescription = null,
            placeholder = painterResource(id = R.drawable.onboarding1),
            modifier =
                Modifier
                    .size(ArticleCardSize)
                    .clip(MaterialTheme.shapes.medium)
                    .weight(0.5f),
        )

        Spacer(modifier = Modifier.weight(0.1f))

        Column(
            verticalArrangement = Arrangement.SpaceAround,
            modifier =
                Modifier
                    .padding(
                        horizontal = ExtraSmallPadding,
                    )
                    .height(ArticleCardSize)
                    .weight(1.4f),
        ) {
            Text(
                text = article.title.toString(),
                style = MaterialTheme.typography.bodyMedium,
                color =
                    colorResource(
                        R.color.text_medium,
                    ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = ExtraSmallPadding2)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = ExtraSmallPadding2)
            ) {
                Text(
                    text = article.source.name,
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    color =
                        colorResource(
                            R.color.text_medium,
                        ),
                )
                Spacer(modifier = Modifier.width(ExtraSmallPadding2))
                Icon(
                    painter = painterResource(id = R.drawable.ic_time),
                    contentDescription = null,
                    modifier = Modifier.size(SmallIconSize),
                    tint =
                        colorResource(
                            R.color.text_medium,
                        ),
                )
                Spacer(modifier = Modifier.width(ExtraSmallPadding2))
                Text(
                    text = " • ${article.publishedAt}",
                    style = MaterialTheme.typography.bodySmall,
                    color =
                        colorResource(
                            R.color.text_medium,
                        ),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ArticleCardPreview() {
    SuperNewsAppTheme {
        ArticleCard(
            article =
                Article(
                    "",
                    "",
                    "",
                    "2 hours",
                    Source("", "BBC"),
                    "Title new BBC hehe",
                    "",
                    "",
                ),
        ) { }
    }
}
