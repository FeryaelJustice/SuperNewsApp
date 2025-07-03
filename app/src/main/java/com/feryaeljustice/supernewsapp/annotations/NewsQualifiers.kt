package com.feryaeljustice.supernewsapp.annotations

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NewsApiKey

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DeeplApiKey
