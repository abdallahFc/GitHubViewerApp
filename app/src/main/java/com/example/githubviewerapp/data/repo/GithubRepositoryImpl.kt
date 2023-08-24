package com.example.githubviewerapp.data.repo

import com.example.githubviewerapp.data.source.local.RepositoryDao
import com.example.githubviewerapp.data.source.local.entities.RepositoryEntity
import com.example.githubviewerapp.data.source.local.mapper.toRepository
import com.example.githubviewerapp.data.source.local.mapper.toRepositoryEntity
import com.example.githubviewerapp.data.source.remote.GithubApiService
import com.example.githubviewerapp.data.source.remote.mapper.toIssue
import com.example.githubviewerapp.data.source.remote.mapper.toRepositoryDetails
import com.example.githubviewerapp.domain.model.Issue
import com.example.githubviewerapp.domain.model.Repository
import com.example.githubviewerapp.domain.model.RepositoryDetails
import com.example.githubviewerapp.domain.repo.GithubRepository
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class GithubRepositoryImpl @Inject constructor(
    private val githubApiService: GithubApiService,
    private val repositoryDao: RepositoryDao
) : GithubRepository {

    override suspend fun getRepositories(): List<Repository> {
        return try {
            val repositoriesFromApi = fetchRepositoriesFromApi()
            clearAndInsertRepositories(repositoriesFromApi)
            retrieveRepositoriesFromDb()
        } catch (exception: Exception) {
            retrieveRepositoriesFromDb()
        }
    }

    private suspend fun fetchRepositoriesFromApi(): List<RepositoryEntity> {
        return wrapResponseWithErrorHandler {
            githubApiService.getRepositories()
        }.map { it.toRepositoryEntity() }
    }

    private suspend fun clearAndInsertRepositories(repositories: List<RepositoryEntity>) {
        repositoryDao.deleteAllRepositories()
        repositoryDao.insertRepositories(repositories)
    }

    private suspend fun retrieveRepositoriesFromDb(): List<Repository> {
        return repositoryDao.getAllRepositories().map { it.toRepository() }
    }


    override suspend fun getRepositoryDetails(owner: String, repo: String): RepositoryDetails {
        return wrapResponseWithErrorHandler {
            githubApiService.getRepositoryDetails(
                owner, repo
            )
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