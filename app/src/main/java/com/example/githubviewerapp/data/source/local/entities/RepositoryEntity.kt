package com.example.githubviewerapp.data.source.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.githubviewerapp.domain.model.User

@Entity(tableName = "repository_table")
data class RepositoryEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val description: String,
    val owner: User
)
