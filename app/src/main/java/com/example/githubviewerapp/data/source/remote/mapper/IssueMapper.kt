package com.example.githubviewerapp.data.source.remote.mapper

import com.example.githubviewerapp.data.source.remote.model.IssueDto
import com.example.githubviewerapp.data.source.remote.model.UserDto
import com.example.githubviewerapp.domain.model.Issue
import com.example.githubviewerapp.domain.model.User



fun IssueDto.toIssue() = Issue(
    id = id.toString(),
    title = title ?: "",
    body = body ?: "",
    createdAt = createdAt ?: "",
    state = state ?: "",
    user = userDto?.toUser() ?: User.createDefault()
)

fun UserDto.toUser() = User(
    id = id.toString(),
    name = login ?: "",
    imageUrl = avatarUrl ?: ""
)
