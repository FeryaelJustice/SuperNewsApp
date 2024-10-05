package com.feryaeljustice.supernewsapp.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.feryaeljustice.supernewsapp.R
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
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @NewsApiKey
    @Provides
    @Singleton
    fun provideNewsApiKey(@ApplicationContext context: Context): String =
        context.getString(R.string.newsApiKey)

    @Provides
    @Singleton
    fun provideNewsAPI(): NewsApi =
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
            .build().create(NewsApi::class.java)

    @Provides
    @Singleton
    fun provideNewsDatabase(
        application: Application
    ): NewsDatabase {
        return Room.databaseBuilder(
            context = application,
            klass = NewsDatabase::class.java,
            name = "news_db"
        ).addTypeConverter(NewsTypeConverter())
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideNewsDao(
        newsDatabase: NewsDatabase
    ): NewsDao = newsDatabase.newsDao
}