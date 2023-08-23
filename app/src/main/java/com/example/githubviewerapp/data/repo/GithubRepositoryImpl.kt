package com.example.githubviewerapp.data.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.githubviewerapp.data.source.local.RepositoryDao
import com.example.githubviewerapp.data.source.local.entities.RepositoryEntity
import com.example.githubviewerapp.data.source.local.mapper.toRepository
import com.example.githubviewerapp.data.source.local.mapper.toRepositoryEntity
import com.example.githubviewerapp.data.source.remote.GithubApiService
import com.example.githubviewerapp.data.source.remote.mapper.toIssue
import com.example.githubviewerapp.data.source.remote.mapper.toRepository
import com.example.githubviewerapp.data.source.remote.mapper.toRepositoryDetails
import com.example.githubviewerapp.domain.model.Issue
import com.example.githubviewerapp.domain.model.Repository
import com.example.githubviewerapp.domain.model.RepositoryDetails
import com.example.githubviewerapp.domain.repo.GithubRepository
import retrofit2.Response
import java.io.IOException
import java.util.concurrent.Flow
import javax.inject.Inject

class GithubRepositoryImpl @Inject constructor(
    private val githubApiService: GithubApiService,
    private val repositoryDao: RepositoryDao
) : GithubRepository {
    override suspend fun getRepositories(): List<Repository> {
        val repositoriesFromDatabase = repositoryDao.getAllRepositories()
        return if (repositoriesFromDatabase.isNotEmpty()) {
            repositoriesFromDatabase.map { it.toRepository() }
        } else {
            val repositoriesFromApi = wrapResponseWithErrorHandler {
                githubApiService.getRepositories()
            }
            repositoryDao.insertRepositories(repositoriesFromApi.map { it.toRepositoryEntity()})
            repositoriesFromApi.map { it.toRepository() }
        }
    }

    override suspend fun getRepositoryDetails(): RepositoryDetails {
        return wrapResponseWithErrorHandler {
            githubApiService.getRepositoryDetails()
        }.toRepositoryDetails()
    }

    override suspend fun getIssues(): List<Issue> {
        return wrapResponseWithErrorHandler {
            githubApiService.getIssues()
        }.map {
            it.toIssue()
        }
    }

    private inline fun <reified T> wrapResponseWithErrorHandler(
        function: () -> Response<T>
    ): T {
        try {
            val response = function()
            if (response.isSuccessful) {
                val baseResponse = response.body()
                return baseResponse ?: throw EmptyResponseException()
            } else {
                val errorBody = response.errorBody()?.string()
                if (errorBody != null) {
                    throw ServerErrorException(errorBody)
                } else {
                    throw ServerErrorException("Unknown server error")
                }
            }
        } catch (e: IOException) {
            throw NetworkErrorException("Network error: ${e.message}")
        } catch (e: Exception) {
            throw UnknownErrorException("An unexpected error occurred")
        }
    }

}