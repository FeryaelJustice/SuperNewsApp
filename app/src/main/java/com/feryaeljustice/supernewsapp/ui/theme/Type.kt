package com.feryaeljustice.supernewsapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.feryaeljustice.supernewsapp.R

val Poppins =
    FontFamily(
        fonts =
            listOf(
                Font(R.font.poppins_regular, FontWeight.Normal),
                Font(R.font.poppins_bold, FontWeight.Bold),
                Font(R.font.poppins_semibold, FontWeight.SemiBold),
            ),
    )

// Set of Material typography styles to start with
val Typography =
    Typography(
        displaySmall =
            TextStyle(
                fontSize = 24.sp,
                fontFamily = Poppins,
                fontWeight = FontWeight.Normal,
                lineHeight = 36.sp,
            ),
        displayMedium =
            TextStyle(
                fontSize = 32.sp,
                fontFamily = Poppins,
                fontWeight = FontWeight.Normal,
                lineHeight = 48.sp,
            ),
        bodySmall =
            TextStyle(
                fontSize = 14.sp,
                fontFamily = Poppins,
                fontWeight = FontWeight.Normal,
                lineHeight = 21.sp,
            ),
        bodyMedium =
            TextStyle(
                fontSize = 16.sp,
                fontFamily = Poppins,
                fontWeight = FontWeight.Normal,
                lineHeight = 24.sp,
            ),
        bodyLarge =
            TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                letterSpacing = 0.5.sp,
            ),
        labelSmall =
            TextStyle(
                fontSize = 13.sp,
                fontFamily = Poppins,
                fontWeight = FontWeight.Normal,
                lineHeight = 19.sp,
            ),
    )
