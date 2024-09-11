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
import com.feryaeldev.supernewsapp.domain.manager.LocalUserManager
import com.feryaeldev.supernewsapp.domain.usecase.AppEntryUseCases
import com.feryaeldev.supernewsapp.domain.usecase.ReadAppEntry
import com.feryaeldev.supernewsapp.domain.usecase.SaveAppEntry
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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

}