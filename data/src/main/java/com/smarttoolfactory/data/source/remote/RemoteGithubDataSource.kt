package com.smarttoolfactory.data.source.remote

import com.smarttoolfactory.data.api.GithubApi
import com.smarttoolfactory.data.model.local.FavoriteRepoEntity
import com.smarttoolfactory.data.model.local.RepoEntity
import com.smarttoolfactory.data.model.remote.response.RepoDTO
import com.smarttoolfactory.data.source.GithubDataSource
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class RemoteGithubDataSource @Inject constructor(private val webService: GithubApi) :
    GithubDataSource {

    override fun getRepoDTOs(user: String): Observable<List<RepoDTO>> {
        return webService.getRepoList(user)
    }

    override fun getRepoEntities(): Observable<List<RepoEntity>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveRepoEntities(repos: List<RepoEntity>): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveRepoEntity(repo: RepoEntity): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteRepos(): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveFavoriteRepo(favoriteRepoEntity: FavoriteRepoEntity): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getFavoriteReposByUser(user: String): Observable<List<FavoriteRepoEntity>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getFavoriteRepos(): Observable<List<FavoriteRepoEntity>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteFavoriteRepo(favoriteRepoEntity: FavoriteRepoEntity): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}