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

// Escala tipográfica oficial basada en Material Design 3 con Poppins
val Typography =
    Typography(
        headlineLarge =
            TextStyle(
                fontFamily = Poppins,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                lineHeight = 36.sp,
                letterSpacing = (-0.5).sp,
            ),
        headlineMedium =
            TextStyle(
                fontFamily = Poppins,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                lineHeight = 30.sp,
                letterSpacing = (-0.25).sp,
            ),
        headlineSmall =
            TextStyle(
                fontFamily = Poppins,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                lineHeight = 28.sp,
            ),
        titleLarge =
            TextStyle(
                fontFamily = Poppins,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                lineHeight = 26.sp,
            ),
        titleMedium =
            TextStyle(
                fontFamily = Poppins,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                lineHeight = 24.sp,
            ),
        titleSmall =
            TextStyle(
                fontFamily = Poppins,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                lineHeight = 20.sp,
            ),
        bodyLarge =
            TextStyle(
                fontFamily = Poppins,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                lineHeight = 26.sp,
                letterSpacing = 0.25.sp,
            ),
        bodyMedium =
            TextStyle(
                fontFamily = Poppins,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                lineHeight = 22.sp,
                letterSpacing = 0.25.sp,
            ),
        bodySmall =
            TextStyle(
                fontFamily = Poppins,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                lineHeight = 18.sp,
                letterSpacing = 0.4.sp,
            ),
        labelLarge =
            TextStyle(
                fontFamily = Poppins,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                lineHeight = 20.sp,
            ),
        labelMedium =
            TextStyle(
                fontFamily = Poppins,
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
                lineHeight = 16.sp,
            ),
        labelSmall =
            TextStyle(
                fontFamily = Poppins,
                fontWeight = FontWeight.Normal,
                fontSize = 11.sp,
                lineHeight = 14.sp,
            ),
        displaySmall =
            TextStyle(
                fontFamily = Poppins,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                lineHeight = 32.sp,
            ),
        displayMedium =
            TextStyle(
                fontFamily = Poppins,
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                lineHeight = 40.sp,
            ),
    )
