package com.smarttoolfactory.data.source

import com.smarttoolfactory.data.model.local.FavoriteRepoEntity
import com.smarttoolfactory.data.model.local.RepoEntity
import com.smarttoolfactory.data.model.remote.response.RepoDTO
import io.reactivex.Completable
import io.reactivex.Observable

interface GithubDataSource {


    // Github Api
    fun getRepoDTOs(user: String): Observable<List<RepoDTO>>

    // Repo Dao

    fun getRepoEntities(): Observable<List<RepoEntity>>

    fun getRepoEntitiesByUser(user: String): Observable<List<RepoEntity>>

    fun saveRepoEntities(repos: List<RepoEntity>): Completable

    fun saveRepoEntity(repo: RepoEntity): Completable

    fun deleteRepos(): Completable

    // Favorite Repos Dao

    fun saveFavoriteRepo(favoriteRepoEntity: FavoriteRepoEntity): Completable

    fun getFavoriteReposByUser(user: String): Observable<List<FavoriteRepoEntity>>

    fun getFavoriteRepos(): Observable<List<FavoriteRepoEntity>>

    fun deleteFavoriteRepo(favoriteRepoEntity: FavoriteRepoEntity): Completable
}