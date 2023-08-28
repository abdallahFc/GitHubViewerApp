package com.example.githubviewerapp.data.source.remote.mapper

import com.example.githubviewerapp.data.source.remote.model.IssueDto
import com.example.githubviewerapp.domain.model.Issue
import com.example.githubviewerapp.domain.model.User



fun IssueDto.toIssue() = Issue(
    id = id.toString(),
    title = title ?: "",
    body = body ?: "",
    createdAt = createdAt ?: "",
    state = state ?: "",
    user = user?.toOwner() ?: User.createDefault()
)