package com.smarttoolfactory.data.factory

import com.smarttoolfactory.data.model.local.FavoriteRepoEntity
import com.smarttoolfactory.data.model.local.RepoEntity
import com.smarttoolfactory.data.model.remote.response.OwnerDTO
import com.smarttoolfactory.data.model.remote.response.RepoDTO

/**
 * Normally Factory methods does not take more than 3 parameters but to create test variations
 * default and multiple params added.
 */
object TestObjectFactory {

    // Owner properties
    private const val ownerId = 35650605
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

    fun getMockFavoriteRepoEntity(): FavoriteRepoEntity = FavoriteRepoEntity(
        repoId = repoId,
        repoName = repoName,
        starCount = repoStars,
        openIssuesCount = openIssuesCount,
        ownerId = ownerId,
        ownerName = login,
        ownerAvatarUrl = avatarUrl
    )

}