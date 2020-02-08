package com.smarttoolfactory.data.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Repos retrieved via REST is converted to [RepoEntity] to store in database for offline-first
 */
@Entity(tableName = "repo")
data class RepoEntity(

    @PrimaryKey
    @ColumnInfo(name = "repo_id")
    val repoId: Int,

    @ColumnInfo(name = "name")
    val repoName: String,

    @ColumnInfo(name = "stargazers_count")
    val starCount: Int,

    @ColumnInfo(name = "open_issues_count")
    val openIssuesCount: Int,

    @ColumnInfo(name = "owner")
    val owner: OwnerEntity,

    @ColumnInfo(name = "favorite")
    var isFavorite: Boolean

)

data class OwnerEntity(
    @ColumnInfo(name = "owner_id")
    val ownerId: Int,

    @ColumnInfo(name = "login")
    val login: String,

    @ColumnInfo(name = "avatar_url")
    val avatarUrl: String
)




