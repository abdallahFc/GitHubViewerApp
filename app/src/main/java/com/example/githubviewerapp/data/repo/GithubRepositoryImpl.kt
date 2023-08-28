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
import com.example.githubviewerapp.domain.util.EmptyResponseException
import com.example.githubviewerapp.domain.util.NetworkErrorException
import com.example.githubviewerapp.domain.util.ServerErrorException
import com.example.githubviewerapp.domain.util.UnknownErrorException

import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class GithubRepositoryImpl @Inject constructor(
    private val githubApiService: GithubApiService,
    private val repositoryDao: RepositoryDao
) : GithubRepository {

    // region getRepositories
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
        return wrapResponse {
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
    // endregion

    // region getRepositoryDetails
    override suspend fun getRepositoryDetails(owner: String, repo: String): RepositoryDetails {
        return wrapResponse {
            githubApiService.getRepositoryDetails(
                owner, repo
            )
        }.toRepositoryDetails()
    }
    // endregion

    // region getIssues
    override suspend fun getIssues(owner: String, repo: String): List<Issue> {
        return wrapResponse {
            githubApiService.getIssues(owner, repo)
        }.map {
            it.toIssue()
        }
    }

    // endregion

    private inline fun <reified T> wrapResponse(
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
                    throw ServerErrorException(ERROR_UNKNOWN_SERVER)
                }
            }
        } catch (e: IOException) {
            throw NetworkErrorException(ERROR_NETWORK)
        } catch (e: Exception) {
            throw UnknownErrorException(ERROR_UNEXPECTED)
        }
    }


    companion object {
        const val ERROR_UNKNOWN_SERVER = "server error"
        const val ERROR_NETWORK = "No Internet Connection"
        const val ERROR_UNEXPECTED = "An unexpected error occurred"
    }

}