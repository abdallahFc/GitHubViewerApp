package com.example.githubviewerapp.ui.feature.home

import app.cash.turbine.test
import com.example.githubviewerapp.domain.model.Repository
import com.example.githubviewerapp.domain.model.User
import com.example.githubviewerapp.domain.repo.GithubRepository
import com.example.githubviewerapp.domain.util.NetworkErrorException
import com.example.githubviewerapp.domain.util.ServerErrorException
import com.example.githubviewerapp.ui.base.ErrorHandler
import com.example.githubviewerapp.ui.feature.util.MainCoroutineRule
import com.example.githubviewerapp.ui.feature.util.TestDispatchers
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {
    private lateinit var mockRepository: GithubRepository
    private lateinit var viewModel: HomeViewModel
    private lateinit var testDispatcher: TestDispatchers
    @get:Rule
    val mainDispatcherRule = MainCoroutineRule()

    @Before
    fun setUp() {
        testDispatcher = TestDispatchers()
        mockRepository = mockk(relaxed = true)
        viewModel = HomeViewModel(mockRepository, testDispatcher)
    }

    @Test
    fun `getAllRepositories() when unsuccessful call, then should return error`() =
        runTest {
            // Given
            val error = "error"
            coEvery { mockRepository.getRepositories() } throws NetworkErrorException(error)
            // When
            viewModel.getAllRepositories()

            delay(10L)

            // Then
            viewModel.state.test {
                assertEquals(true, awaitItem().isError)
            }
        }

    @Test
    fun `getAllRepositories() when server error, then should return error state`() =
        runTest {
            // Given
            val error = "server error"
            coEvery { mockRepository.getRepositories() } throws ServerErrorException(error)
            // When
            viewModel.getAllRepositories()

            delay(10L)

            // Then
            viewModel.state.test {
                val actualState = awaitItem()
                assertEquals(
                    HomeUiState(
                        repositories = emptyList(),
                        isError = true,
                        error = ErrorHandler.ServerError(error)
                    ),
                    actualState
                )
            }
        }

    @Test
    fun `getAllRepositories() when successful call with empty list, then should return empty list of repositories`() =
        runTest {
            // Given
            val emptyList = emptyList<Repository>()
            coEvery { mockRepository.getRepositories() } returns emptyList
            // When
            viewModel.getAllRepositories()

            delay(10L)

            // Then
            viewModel.state.test {
                assertEquals(true, awaitItem().isEmpty)
            }
        }

    @Test
    fun `getAllRepositories() when successful call, then should return list of repositories`() =
        runTest {
            // Given
            val expectedRepositories = listOf(
                Repository(
                    "1", "name", "description", User(
                        "12", "userName", "avatarUrl"
                    )
                ),
                Repository(
                    "1", "name", "description", User(
                        "12", "userName", "avatarUrl"
                    )
                )
            )
            coEvery { mockRepository.getRepositories() } returns expectedRepositories
            // When
            viewModel.getAllRepositories()

            delay(10L)

            // Then
            viewModel.state.test {
                val actualState = awaitItem()
                assertEquals(
                    HomeUiState(
                        repositories = expectedRepositories.map { it.toUiModel() },
                        isError = false
                    ),
                    actualState
                )
            }
        }

    @Test
    fun `getAllRepositories() when loading, then should return loading state`() =
        runTest {
            // Given
            coEvery { mockRepository.getRepositories() } coAnswers {
                emptyList()
            }
            // When
            viewModel.getAllRepositories()

            // Then
            viewModel.state.test {
                val loadingState = awaitItem()
                assertEquals(
                    HomeUiState(isLoading = true),
                    loadingState
                )
            }
        }
    @Test
    fun `onClickRepositoryItem() should emit NavigateToRepositoryDetails effect`()
    =runTest {
        // Given
        val owner = "owner"
        val repositoryName = "repositoryName"

        // When
        viewModel.onClickRepositoryItem(owner, repositoryName)

        // Then
        viewModel.effect.test {
            val effect = awaitItem()
            assertTrue(effect is HomeUiEffect.NavigateToRepositoryDetails)
        }
    }


}


