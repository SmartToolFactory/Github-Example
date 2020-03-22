package com.smarttoolfactory.data.factory

import com.smarttoolfactory.data.model.local.FavoriteRepoEntity
import com.smarttoolfactory.data.model.local.RepoEntity
import com.smarttoolfactory.data.model.remote.response.OwnerDTO
import com.smarttoolfactory.data.model.remote.response.RepoDTO

/**
 * Normally Factory methods do not take more than 3 parameters but to create test variations
 * default and multiple params added.
 */
object TestObjectFactory {

    // Owner properties
     const val ownerId = 35650605
    private const val login = "SmartToolFactory"
    private const val avatarUrl = "https://avatars0.githubusercontent.com/u/35650605?v=4"

    // Repo properties
    private const val repoId = 169870520
    private const val repoName = "MVVM-Tutorials"
    private const val repoStars = 3
    private const val openIssuesCount = 0


    fun getMockOwnerDTO(): OwnerDTO =
        OwnerDTO(ownerId = ownerId, login = login, avatarUrl = avatarUrl)


    fun getMockRepoDTO(): RepoDTO =
        RepoDTO(
            repoId = repoId,
            name = repoName,
            stars = repoStars,
            issueCount = openIssuesCount,
            owner = getMockOwnerDTO()
        )


    fun getMockRepoEntity(isFavorite: Boolean = false): RepoEntity =
        RepoEntity(
            repoId = repoId,
            repoName = repoName,
            starCount = repoStars,
            openIssuesCount = openIssuesCount,
            ownerId = ownerId,
            login = login,
            avatarUrl = avatarUrl,
            isFavorite = isFavorite
        )

    fun getMockFavoriteRepoEntity(ownerUID: Int = ownerId, repoUID:Int= repoId, repoTitle: String = repoName): FavoriteRepoEntity = FavoriteRepoEntity(
        repoId = repoUID,
        repoName = repoTitle,
        starCount = repoStars,
        openIssuesCount = openIssuesCount,
        ownerId = ownerUID,
        ownerName = login,
        ownerAvatarUrl = avatarUrl
    )

}