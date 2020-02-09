package com.smarttoolfactory.data.repository

import com.smarttoolfactory.data.model.local.FavoriteRepoEntity
import com.smarttoolfactory.data.model.local.RepoEntity
import com.smarttoolfactory.data.model.remote.response.RepoDTO
import io.reactivex.Completable
import io.reactivex.Single

interface GithubRepository {

    // Github Api
    fun getRepoDTOs(user: String): Single<List<RepoDTO>>

    // Repo Dao
    fun getRepoEntities(): Single<List<RepoEntity>>

    fun deleteRepos(): Completable

    // Favorite Repos Dao

    fun setFavoriteStatus(favoriteRepoEntity: FavoriteRepoEntity): Completable

    fun getFavoriteReposByUser(user: String): Single<List<FavoriteRepoEntity>>

    fun getFavoriteRepos(): Single<List<FavoriteRepoEntity>>

}