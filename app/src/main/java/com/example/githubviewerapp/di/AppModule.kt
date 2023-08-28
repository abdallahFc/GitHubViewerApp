package com.example.githubviewerapp.di

import com.example.githubviewerapp.ui.util.DispatcherProvider
import com.example.githubviewerapp.ui.util.StandardDispatcher
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
    fun provideStandardProvider(): DispatcherProvider {
        return StandardDispatcher()
    }
}