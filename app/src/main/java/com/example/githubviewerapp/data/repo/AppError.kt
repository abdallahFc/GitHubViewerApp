package com.example.githubviewerapp.data.repo

sealed class AppError(message: String) : Exception(message)

class NetworkErrorException(message: String) : AppError(message)
class EmptyResponseException : AppError("Empty response received")
class ServerErrorException(message: String) : AppError(message)
class UnknownErrorException(message: String) : AppError(message)
