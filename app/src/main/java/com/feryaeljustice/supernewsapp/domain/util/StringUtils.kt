package com.feryaeljustice.supernewsapp.domain.util

fun String.removeTrailingCharsIndicator(): String =
    this.replace(Regex("""\s*\[\+\d+\s+chars]$"""), "")