package com.example.githubviewerapp.di

import android.content.Context
import androidx.room.Room
import com.example.githubviewerapp.data.source.local.GithubDataBase
import com.example.githubviewerapp.data.source.local.RepositoryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun providesRoomDatabase(
        @ApplicationContext context: Context,
    ): GithubDataBase {
        return Room.databaseBuilder(context, GithubDataBase::class.java, "GithubDatabase")
            .build()
    }

    @Singleton
    @Provides
    fun provideUserDao(database: GithubDataBase): RepositoryDao {
        return database.repositoryDao
    }
}