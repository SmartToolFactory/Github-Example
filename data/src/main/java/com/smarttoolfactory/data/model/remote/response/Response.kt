package com.smarttoolfactory.data.model.remote.response

import com.google.gson.annotations.SerializedName

data class RepoDTO(
    @SerializedName("id")
    val repoId: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("stargazers_count")
    val stars: Int,
    @SerializedName("open_issues_count")
    val issueCount: Int,
    @SerializedName("owner")
    val owner: OwnerDTO
)


data class OwnerDTO(
    @SerializedName("id")
    val ownerId: Int,
    @SerializedName("login")
    val login: String,
    @SerializedName("avatar_url")
    val avatarUrl: String
)



