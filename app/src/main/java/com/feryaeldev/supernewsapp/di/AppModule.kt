/*
 * Copyright (c) 2024. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.feryaeldev.supernewsapp.di

import android.app.Application
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
import com.feryaeldev.supernewsapp.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLocalUserManager(application: Application): LocalUserManager =
        LocalUserManagerImpl(application)

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
    fun provideNewsRepository(newsApi: NewsApi): NewsRepository = NewsRepositoryImpl(newsApi)

    @Provides
    @Singleton
    fun provideNewsUseCases(newsRepository: NewsRepository) = NewsUseCases(GetNews(newsRepository))
}