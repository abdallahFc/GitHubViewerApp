package com.example.githubviewerapp.data.source.remote.model

import com.google.gson.annotations.SerializedName

data class IssueDto(
    @SerializedName("author_association") val authorAssociation: String?,
    val body: String?,
    @SerializedName("comments") val commentsCount: Int?,
    @SerializedName("comments_url") val commentsUrl: String?,
    @SerializedName("created_at") val createdAt: String?,
    val isDraft: Boolean?,
    @SerializedName("events_url") val eventsUrl: String?,
    @SerializedName("html_url") val htmlUrl: String?,
    val id: Int?,
    @SerializedName("labels_url") val labelsUrl: String?,
    @SerializedName("locked") val isLocked: Boolean?,
    @SerializedName("node_id") val nodeId: String?,
    val number: Int?,
    @SerializedName("pull_request") val pullRequest: PullRequest?,
    @SerializedName("repository_url") val repositoryUrl: String?,
    val state: String?,
    @SerializedName("timeline_url") val timelineUrl: String?,
    val title: String?,
    @SerializedName("updated_at") val updatedAt: String?,
    val url: String?,
    val userDto: UserDto?
)
