package com.example.githubviewerapp.domain.model

import com.example.githubviewerapp.domain.model.User


data class User(
    val id: String,
    val name: String,
    val imageUrl: String
) {
    companion object {
        fun createDefault() = User("", "", "")
    }
}

