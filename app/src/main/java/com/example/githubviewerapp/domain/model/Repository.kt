package com.example.githubviewerapp.domain.model

data class Repository(
    val id: String,
    val name: String,
    val description: String,
    val owner: User
)
