package com.example.githubviewerapp.ui.feature.util

import com.example.githubviewerapp.ui.util.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher

@ExperimentalCoroutinesApi
class TestDispatchers: DispatcherProvider {
    private val testDispatcher = StandardTestDispatcher()

    override val main: CoroutineDispatcher
        get() = testDispatcher
    override val io: CoroutineDispatcher
        get() = testDispatcher
    override val default: CoroutineDispatcher
        get() = testDispatcher
    override val unconfined: CoroutineDispatcher
        get() = testDispatcher
}