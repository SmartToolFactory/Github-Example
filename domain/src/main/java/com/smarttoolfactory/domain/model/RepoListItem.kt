package com.smarttoolfactory.domain.model

/**
 * Data class for displaying items on UI
 */
data class RepoListItem(

    val repoId: Int,

    val repoName: String,

    val starCount: Int,

    val openIssuesCount: Int,

    var isFavorite: Boolean,

    val ownerId: Int,

    val login: String,

    val avatarUrl: String
)