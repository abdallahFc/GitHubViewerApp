package com.example.githubviewerapp.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubviewerapp.data.repo.EmptyResponseException
import com.example.githubviewerapp.data.repo.NetworkErrorException
import com.example.githubviewerapp.data.repo.ServerErrorException
import com.example.githubviewerapp.data.repo.UnknownErrorException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<T, E>(initialState: T) : ViewModel() {
    protected val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    protected val _effect = MutableSharedFlow<E>()
    val effect = _effect.asSharedFlow()

    protected fun <T> tryToExecute(
        function: suspend () -> T,
        onSuccess: (T) -> Unit,
        onError: (t: String) -> Unit,
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
    ) {
        viewModelScope.launch(dispatcher) {
            try {
                val result = function()
                onSuccess(result)
            } catch (exception: NetworkErrorException) {
                onError(exception.message ?: "Error")
            } catch (exception: EmptyResponseException) {
                onError(exception.message ?: "Error")
            } catch (exception: ServerErrorException) {
                onError(exception.message ?: "Error")
            } catch (exception: UnknownErrorException) {
                onError(exception.message ?: "Error")
            }
        }

    }
    protected fun <T : BaseUiEffect> effectActionExecutor(
        _effect: MutableSharedFlow<T>,
        effect: T,
    ) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }
}
