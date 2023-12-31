package com.example.githubviewerapp.data.source.local


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.githubviewerapp.data.source.local.entities.RepositoryEntity

@Dao
interface RepositoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepositories(repositories: List<RepositoryEntity>)

    @Query("SELECT * FROM repository_table")
    suspend fun getAllRepositories(): List<RepositoryEntity>

    @Query("DELETE FROM repository_table")
    suspend fun deleteAllRepositories()
}
