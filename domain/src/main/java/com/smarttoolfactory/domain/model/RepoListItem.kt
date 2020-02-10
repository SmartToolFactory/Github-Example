package com.smarttoolfactory.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Data class for displaying items on UI
 */

@Parcelize
data class RepoListItem(

    val repoId: Int,

    val repoName: String,

    val starCount: Int,

    val openIssuesCount: Int,

    var isFavorite: Boolean,

    val ownerId: Int,

    val login: String,

    val avatarUrl: String
) : Parcelable