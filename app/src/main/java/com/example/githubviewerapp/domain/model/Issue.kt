package com.example.githubviewerapp.domain.model

import com.example.githubviewerapp.domain.model.User

data class Issue(
    val id: String,
    val title: String,
    val body: String,
    val state: String,
    val createdAt: String,
    val user: User
)
