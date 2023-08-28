package com.example.githubviewerapp.data.source.remote

import com.example.githubviewerapp.data.source.remote.model.IssueDto
import com.example.githubviewerapp.data.source.remote.model.RepoDetailsDto
import com.example.githubviewerapp.data.source.remote.model.RepositoryDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApiService {
    @GET("repositories")
    suspend fun getRepositories(): Response<List<RepositoryDto>>

    @GET("/repos/{owner}/{repo_name}")
    suspend fun getRepositoryDetails(
        @Path("owner") owner: String,
        @Path("repo_name") repo: String
    ): Response<RepoDetailsDto>

    @GET("/repos/{owner}/{repo}/issues")
    suspend fun getIssues(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Response<List<IssueDto>>

}