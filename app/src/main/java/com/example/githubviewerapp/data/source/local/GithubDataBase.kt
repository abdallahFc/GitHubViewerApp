package com.example.githubviewerapp.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.githubviewerapp.data.source.local.entities.RepositoryEntity


@Database(
    entities = [RepositoryEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class GithubDataBase : RoomDatabase() {
    abstract val repositoryDao: RepositoryDao
}