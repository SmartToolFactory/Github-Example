package com.smarttoolfactory.data.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repos")
data class RepoEntity(

    @PrimaryKey
    val repoId: Int,

    val repoName: String,

    val starCount: Int,

    val openIssueCount: Int,

    var isFavorite: Boolean,

    val ownerId: Int,
    val ownerName: String,
    val ownerAvatarUrl: String


)




