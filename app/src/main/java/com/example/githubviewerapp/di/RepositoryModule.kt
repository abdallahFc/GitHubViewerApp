package com.example.githubviewerapp.di

import com.example.githubviewerapp.data.repo.GithubRepositoryImpl
import com.example.githubviewerapp.domain.repo.GithubRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindGithubRepository(repository: GithubRepositoryImpl): GithubRepository
}