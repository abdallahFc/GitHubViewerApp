package com.example.githubviewerapp.domain.model

import com.example.githubviewerapp.domain.model.User

data class RepositoryDetails(
    val id: String,
    val name: String,
    val description: String,
    val language: String,
    val starCount: Int,
    val watchersCount: Int,
    val forksCount: Int,
    val openIssuesCount: Int,
    val subscribersCount: Int,
    val createdAt: String,
    val owner: User,
)
