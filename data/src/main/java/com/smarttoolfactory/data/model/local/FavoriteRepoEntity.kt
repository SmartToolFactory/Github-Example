package com.smarttoolfactory.data.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Table for storing favorites repos selected. If repo id is equal to one of repos then they match.
 */
@Entity(tableName = "favorite")
data class FavoriteRepoEntity(

    @PrimaryKey
    @ColumnInfo(name = "repo_id")
    val repoId: Int,

    @ColumnInfo(name = "name")
    val repoName: String,

    @ColumnInfo(name = "stargazers_count")
    val starCount: Int,

    @ColumnInfo(name = "open_issues_count")
    val openIssuesCount: Int,

    @ColumnInfo(name = "owner_id")
    val ownerId: Int,

    @ColumnInfo(name = "login")
    val ownerName: String,

    @ColumnInfo(name = "avatar_url")
    val ownerAvatarUrl: String
)
