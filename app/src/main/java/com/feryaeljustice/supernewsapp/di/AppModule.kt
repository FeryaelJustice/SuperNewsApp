package com.feryaeljustice.supernewsapp.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.feryaeljustice.supernewsapp.R
import com.feryaeljustice.supernewsapp.annotations.DeeplApiKey
import com.feryaeljustice.supernewsapp.annotations.NewsApiKey
import com.feryaeljustice.supernewsapp.data.local.NewsDao
import com.feryaeljustice.supernewsapp.data.local.NewsDatabase
import com.feryaeljustice.supernewsapp.data.local.NewsTypeConverter
import com.feryaeljustice.supernewsapp.data.remote.NewsApi
import com.feryaeljustice.supernewsapp.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Locale
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideLocale(
        @ApplicationContext context: Context,
    ): String = Locale.getDefault().language

    @NewsApiKey
    @Provides
    @Singleton
    fun provideNewsApiKey(
        @ApplicationContext context: Context,
    ): String = context.getString(R.string.newsApiKey)

    @DeeplApiKey
    @Provides
    @Singleton
    fun provideDeeplApiKey(
        @ApplicationContext context: Context,
    ): String = context.getString(R.string.deeplApiKey)

    @Provides
    @Singleton
    fun provideNewsAPI(): NewsApi =
        Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi::class.java)

    @Provides
    @Singleton
    fun provideNewsDatabase(application: Application): NewsDatabase =
        Room
            .databaseBuilder(
                context = application,
                klass = NewsDatabase::class.java,
                name = "news_db",
            ).addTypeConverter(NewsTypeConverter())
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideNewsDao(newsDatabase: NewsDatabase): NewsDao = newsDatabase.newsDao
}
