package com.feryaeljustice.supernewsapp.presentation.onboarding.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.feryaeljustice.supernewsapp.presentation.Dimens
import com.feryaeljustice.supernewsapp.presentation.onboarding.Page
import com.feryaeljustice.supernewsapp.presentation.onboarding.pages
import com.feryaeljustice.supernewsapp.ui.theme.SuperNewsAppTheme

@Composable
fun OnBoardingPage(
    modifier: Modifier = Modifier,
    page: Page,
) {
    Column(modifier = modifier) {
        Image(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(fraction = 0.58f),
            painter = painterResource(id = page.image),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center,
        )
        Spacer(modifier = Modifier.height(Dimens.SmallPadding1))
        Text(
            text = stringResource(id = page.titleRes),
            modifier = Modifier.padding(horizontal = Dimens.MediumPadding2),
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 3,
            overflow = TextOverflow.Clip,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(id = page.descriptionRes),
            modifier = Modifier.padding(horizontal = Dimens.MediumPadding2),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Preview(showBackground = true)
@Preview(name = "Night", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun OnBoardingPagePreview() {
    SuperNewsAppTheme {
        OnBoardingPage(page = pages[0])
    }
}
