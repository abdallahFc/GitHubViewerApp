package com.example.githubviewerapp.ui.feature.issue

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.example.githubviewerapp.domain.model.Issue
import com.example.githubviewerapp.domain.model.User
import com.example.githubviewerapp.domain.repo.GithubRepository
import com.example.githubviewerapp.domain.util.NetworkErrorException
import com.example.githubviewerapp.ui.base.ErrorHandler
import com.example.githubviewerapp.ui.feature.util.MainCoroutineRule
import com.example.githubviewerapp.ui.feature.util.TestDispatchers
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class IssuesViewModelTest {
    private lateinit var mockRepository: GithubRepository
    private lateinit var viewModel: IssuesViewModel
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
        viewModel = IssuesViewModel(mockRepository, testDispatcher, savedStateHandle)
    }

    @Test
    fun `getAllIssues() when unsuccessful call, then should return error`() =
        runTest {
            // Given
            val error = "error"
            coEvery {
                mockRepository.getIssues("owner", "repoName")
            } throws NetworkErrorException(error)
            // When
            viewModel.getAllIssues()

            delay(10L)

            // Then
            viewModel.state.test {
                val actual = awaitItem()
                assertEquals(
                    IssuesUiState(
                        isLoading = false,
                        isError = true,
                        error = ErrorHandler.NetworkError(error),
                    ), actual
                )
            }
        }

    @Test
    fun `getAllIssues() when successful call with empty list, then should return empty list of issues`() =
        runTest {
            // Given
            coEvery {
                mockRepository.getIssues("owner", "repoName")
            } returns emptyList()
            // When
            viewModel.getAllIssues()

            delay(10L)

            // Then
            viewModel.state.test {
                val actual = awaitItem()
                assertEquals(
                    IssuesUiState(
                        isLoading = false,
                        isError = false,
                        isEmpty = true,
                        error = null,
                        items = emptyList()
                    ), actual
                )
            }
        }
    @Test
    fun `getAllIssues() when successful call with data, then should return list of issues`() =
        runTest {
            // Given
            val expectedIssues = listOf(
                Issue(
                    id = "id",
                    title = "title",
                    state = "state",
                    createdAt = "createdAt",
                    user = User( "id", "user", "avatarUrl"),
                    body = "body"
                )
            )
            coEvery {
                mockRepository.getIssues("owner", "repoName")
            } returns expectedIssues
            // When
            viewModel.getAllIssues()

            delay(10L)

            // Then
            viewModel.state.test {
                val actual = awaitItem()
                assertEquals(
                    IssuesUiState(
                        isLoading = false,
                        isError = false,
                        isEmpty = false,
                        error = null,
                        items = expectedIssues.map { it.toUiModel() }
                    ), actual
                )
            }
        }

    @Test
    fun `getAllIssues() when loading, then should return loading state`() =
        runTest {
            // Given
            coEvery { mockRepository.getIssues("owner", "repoName") } coAnswers {
                emptyList()
            }
            // When
            viewModel.getAllIssues()

            // Then
            viewModel.state.test {
                val loadingState = awaitItem()
                assertEquals(
                    IssuesUiState(isLoading = true),
                    loadingState
                )
            }
        }

}