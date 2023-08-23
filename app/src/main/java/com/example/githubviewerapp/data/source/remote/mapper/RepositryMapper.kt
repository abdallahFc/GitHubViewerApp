package com.example.githubviewerapp.data.source.remote.mapper

import com.example.githubviewerapp.data.source.remote.model.OwnerDto
import com.example.githubviewerapp.data.source.remote.model.RepositoryDto
import com.example.githubviewerapp.domain.model.User
import com.example.githubviewerapp.domain.model.Repository


fun RepositoryDto.toRepository() = Repository(
    id = id.toString(),
    name = name ?: "",
    description = description ?: "",
    owner = owner?.toOwner() ?: User.createDefault(),
)

fun OwnerDto.toOwner() = User(
    id = id.toString(),
    name = login ?: "",
    imageUrl = avatarUrl ?: ""
)