package com.feryaeljustice.supernewsapp.di

import com.feryaeljustice.supernewsapp.data.manager.LocalUserManagerImpl
import com.feryaeljustice.supernewsapp.domain.manager.LocalUserManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ManagerModule {
    @Binds
    @Singleton
    abstract fun bindLocalUserManger(localUserMangerImpl: LocalUserManagerImpl): LocalUserManager
}
