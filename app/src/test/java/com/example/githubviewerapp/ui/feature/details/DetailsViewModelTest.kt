package com.example.githubviewerapp.ui.feature.details

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.example.githubviewerapp.domain.model.RepositoryDetails
import com.example.githubviewerapp.domain.model.User
import com.example.githubviewerapp.domain.repo.GithubRepository
import com.example.githubviewerapp.domain.util.NetworkErrorException
import com.example.githubviewerapp.ui.base.ErrorHandler
import com.example.githubviewerapp.ui.feature.util.MainCoroutineRule
import com.example.githubviewerapp.ui.feature.util.TestDispatchers
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DetailsViewModelTest {
    private lateinit var mockRepository: GithubRepository
    private lateinit var viewModel: DetailsViewModel
    private lateinit var testDispatcher: TestDispatchers
    private lateinit var savedStateHandle: SavedStateHandle

    @get:Rule
    val mainDispatcherRule = MainCoroutineRule()

    @Before
    fun setUp() {
        testDispatcher = TestDispatchers()
        mockRepository = mockk(relaxed = true)
        // Mock SavedStateHandle
        savedStateHandle = mapOf(
            "owner" to "owner",
            "repoName" to "repoName"
        ).let { SavedStateHandle(it) }
        viewModel = DetailsViewModel(mockRepository, testDispatcher, savedStateHandle)
    }

    @Test
    fun `getRepositoryDetails() when unsuccessful call, then should return error`() =
        runTest {
            // Given
            val error = "error"
            coEvery {
                mockRepository.getRepositoryDetails(
                    "owner",
                    "repoName"
                )
            } throws NetworkErrorException(error)
            // When
            viewModel.getRepositoryDetails()
            delay(10L)
            //then
            viewModel.state.test {
                val actual = awaitItem()
                assertEquals(
                    DetailsUiState(
                        owner = "owner",
                        repoName = "repoName",
                        isLoading = false,
                        isError = true,
                        error = ErrorHandler.NetworkError(error),
                    ), actual
                )
            }
        }

    @Test
    fun `getRepositoryDetails() when successful call, then should update state with data`() =
        runTest {
            // Given
            val repositoryDetails = RepositoryDetails(
                id = "id",
                name = "repoName",
                description = "description",
                language = "language",
                starCount = 1,
                watchersCount = 1,
                forksCount = 1,
                openIssuesCount = 1,
                subscribersCount = 1,
                createdAt = "createdAt",
                owner = User(
                    "id", "owner", "avatarUrl"
                )
            )
            coEvery {
                mockRepository.getRepositoryDetails(
                    "owner",
                    "repoName"
                )
            } returns repositoryDetails
            // When
            viewModel.getRepositoryDetails()
            delay(10L)
            // Then
            viewModel.state.test {
                val actual = awaitItem()
                assertEquals(
                    DetailsUiState(
                        owner = "owner",
                        repoName = "repoName",
                        isLoading = false,
                        isError = false,
                        item = repositoryDetails.toUiModel(),
                    ), actual
                )
            }
        }

    @Test
    fun `getRepositoryDetails() when calling, then should update loading state to true`() =
        runTest {
            // Given
            val repositoryDetails = RepositoryDetails(
                id = "id",
                name = "repoName",
                description = "description",
                language = "language",
                starCount = 1,
                watchersCount = 1,
                forksCount = 1,
                openIssuesCount = 1,
                subscribersCount = 1,
                createdAt = "createdAt",
                owner = User(
                    "id", "owner", "avatarUrl"
                )
            )
            coEvery {
                mockRepository.getRepositoryDetails(
                    "owner",
                    "repoName"
                )
            } returns repositoryDetails

            // When
            viewModel.getRepositoryDetails()

            // Then
            viewModel.state.test {
                val loadingState = awaitItem()
                assertTrue(loadingState.isLoading)
            }
        }
    @Test
    fun `onClickIssues() should emit NavigateToIssues effect`()
            =runTest {
        // Given
        val owner = "owner"
        val repositoryName = "repositoryName"

        // When
        viewModel.onClickIssues(owner, repositoryName)

        // Then
        viewModel.effect.test {
            val effect = awaitItem()
            TestCase.assertTrue(effect is DetailsUiEffect.NavigateToIssues)
        }
    }
}
