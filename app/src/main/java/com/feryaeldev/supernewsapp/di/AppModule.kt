package com.feryaeldev.supernewsapp.di

import android.app.Application
import androidx.room.Room
import com.feryaeldev.supernewsapp.data.local.NewsDao
import com.feryaeldev.supernewsapp.data.local.NewsDatabase
import com.feryaeldev.supernewsapp.data.local.NewsTypeConverter
import com.feryaeldev.supernewsapp.data.remote.NewsApi
import com.feryaeldev.supernewsapp.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

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