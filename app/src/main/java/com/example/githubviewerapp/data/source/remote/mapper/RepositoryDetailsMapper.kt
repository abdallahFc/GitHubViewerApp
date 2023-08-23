package com.example.githubviewerapp.data.source.remote.mapper

import com.example.githubviewerapp.data.source.remote.model.RepoDetailsDto
import com.example.githubviewerapp.domain.model.User
import com.example.githubviewerapp.domain.model.RepositoryDetails

fun RepoDetailsDto.toRepositoryDetails() = RepositoryDetails(
    id = id.toString(),
    name = name ?: "",
    description = description ?: "",
    owner = owner?.toOwner() ?: User.createDefault(),
    starCount = stargazersCount ?: 0,
    openIssuesCount = openIssuesCount ?: 0,
    forksCount = forksCount ?: 0,
    watchersCount = watchersCount ?: 0,
    language = language ?: "",
    createdAt = createdAt ?: "",
    subscribersCount = subscribersCount ?: 0,
)