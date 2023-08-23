package com.example.githubviewerapp.data.source.local.mapper

import com.example.githubviewerapp.data.source.local.entities.RepositoryEntity
import com.example.githubviewerapp.data.source.remote.mapper.toOwner
import com.example.githubviewerapp.data.source.remote.model.RepositoryDto
import com.example.githubviewerapp.domain.model.Repository
import com.example.githubviewerapp.domain.model.User

fun RepositoryEntity.toRepository() = Repository(
    id = id,
    name = name,
    description = description,
    owner = owner
)
fun RepositoryDto.toRepositoryEntity() = RepositoryEntity(
    id = id.toString(),
    name = name ?: "",
    description = description ?: "",
    owner = owner?.toOwner() ?: User.createDefault(),
)

