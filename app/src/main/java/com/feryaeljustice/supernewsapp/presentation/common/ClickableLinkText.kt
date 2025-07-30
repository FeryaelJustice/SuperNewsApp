/*
 * Copyright (c) 2025. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.feryaeljustice.supernewsapp.presentation.common

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit

@Composable
fun ClickableLinkText(
    text: String,
    url: String,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    color: Color = Color.Cyan,
    fontWeight: FontWeight = FontWeight.Bold,
    textDecoration: TextDecoration = TextDecoration.Underline,
    fontSize: TextUnit = TextUnit.Unspecified,
) {
    Text(
        text = buildAnnotatedString {
            append(text)
            addStyle(
                style = SpanStyle(
                    color = color,
                    fontWeight = fontWeight,
                    textDecoration = textDecoration
                ),
                start = 0,
                end = text.length
            )
            addStringAnnotation("URL", url, 0, text.length)
        },
        modifier = modifier.clickable(onClickLabel = text) {
            onClick(url)
        },
        fontSize = fontSize,
    )
}