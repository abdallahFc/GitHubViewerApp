package com.example.githubviewerapp.domain.repo

import com.example.githubviewerapp.domain.model.Issue
import com.example.githubviewerapp.domain.model.Repository
import com.example.githubviewerapp.domain.model.RepositoryDetails

interface GithubRepository {
    suspend fun getRepositories(): List<Repository>
    suspend fun getRepositoryDetails(owner:String,repo:String): RepositoryDetails
    suspend fun getIssues(): List<Issue>
}