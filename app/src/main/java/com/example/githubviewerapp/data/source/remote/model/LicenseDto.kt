package com.example.githubviewerapp.data.source.remote.model

import com.google.gson.annotations.SerializedName

data class LicenseDto(
    val key: String?,
    val name: String?,
    @SerializedName("spdx_id") val spdxId: String?,
    val url: String?,
    @SerializedName("node_id") val nodeId: String?
)
