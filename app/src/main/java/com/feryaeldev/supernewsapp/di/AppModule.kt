/*
 * Copyright (c) 2024. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.feryaeldev.supernewsapp.di

import android.app.Application
import androidx.room.Room
import com.feryaeldev.supernewsapp.data.local.NewsDao
import com.feryaeldev.supernewsapp.data.local.NewsDatabase
import com.feryaeldev.supernewsapp.data.local.NewsTypeConverter
import com.feryaeldev.supernewsapp.data.manager.LocalUserManagerImpl
import com.feryaeldev.supernewsapp.data.remote.NewsApi
import com.feryaeldev.supernewsapp.data.repository.NewsRepositoryImpl
import com.feryaeldev.supernewsapp.domain.manager.LocalUserManager
import com.feryaeldev.supernewsapp.domain.repository.NewsRepository
import com.feryaeldev.supernewsapp.domain.usecase.app_entry.AppEntryUseCases
import com.feryaeldev.supernewsapp.domain.usecase.app_entry.ReadAppEntry
import com.feryaeldev.supernewsapp.domain.usecase.app_entry.SaveAppEntry
import com.feryaeldev.supernewsapp.domain.usecase.news.GetNews
import com.feryaeldev.supernewsapp.domain.usecase.news.NewsUseCases
import com.feryaeldev.supernewsapp.domain.usecase.news.SearchNews
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
    fun provideAppEntryUsesCases(localUserManager: LocalUserManager) =
        AppEntryUseCases(ReadAppEntry(localUserManager), SaveAppEntry(localUserManager))

    @Provides
    @Singleton
    fun provideNewsAPI(): NewsApi =
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
            .build().create(NewsApi::class.java)

    @Provides
    @Singleton
    fun provideNewsUseCases(newsRepository: NewsRepository) =
        NewsUseCases(getNews = GetNews(newsRepository), searchNews = SearchNews(newsRepository))

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