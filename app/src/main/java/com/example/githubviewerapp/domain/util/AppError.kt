package com.example.githubviewerapp.domain.util

sealed class AppError(message: String) : Exception(message)
class NetworkErrorException(message: String) : AppError(message)
class EmptyResponseException : AppError("Op's..! No Content Found")
class ServerErrorException(message: String) : AppError(message)
class UnknownErrorException(message: String) : AppError(message)
