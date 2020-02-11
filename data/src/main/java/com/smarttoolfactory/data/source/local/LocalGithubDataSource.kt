package com.smarttoolfactory.data.source.local


import com.smarttoolfactory.data.model.local.FavoriteRepoEntity
import com.smarttoolfactory.data.model.local.RepoEntity
import com.smarttoolfactory.data.model.remote.response.RepoDTO
import com.smarttoolfactory.data.source.GithubDataSource
import com.smarttoolfactory.data.source.local.dao.FavoriteRepoDao
import com.smarttoolfactory.data.source.local.dao.RepoDao
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

/**
 * TODO Figure a way to uncouple remote and local methods
 */
class LocalGithubDataSource @Inject constructor(
    private val repoDao: RepoDao,
    private val favoriteRepoDao: FavoriteRepoDao
) :
    GithubDataSource {

    override fun getRepoDTOs(user: String): Observable<List<RepoDTO>> {
        TODO()
    }

    override fun getRepoEntities(): Observable<List<RepoEntity>> {
        return repoDao.getRepos()
    }

    override fun getRepoEntitiesByUser(user: String): Observable<List<RepoEntity>> {
        return repoDao.getRepos(user)
    }

    override fun saveRepoEntities(repos: List<RepoEntity>): Completable {
        return repoDao.insertCompletable(repos)
    }

    override fun saveRepoEntity(repo: RepoEntity): Completable {
        return repoDao.insertCompletable(repo)
    }

    override fun deleteRepos(): Completable {
        return repoDao.deleteAllCompletable()
    }

    override fun saveFavoriteRepo(favoriteRepoEntity: FavoriteRepoEntity): Completable {
        return favoriteRepoDao.insertCompletable(favoriteRepoEntity)
    }

    override fun getFavoriteReposByUser(user: String): Observable<List<FavoriteRepoEntity>> {
        return favoriteRepoDao.getFavoriteReposByUser(user)
    }

    override fun getFavoriteRepos(): Observable<List<FavoriteRepoEntity>> {
        return favoriteRepoDao.getFavoriteRepos()
    }

    override fun deleteFavoriteRepo(favoriteRepoEntity: FavoriteRepoEntity): Completable {
        return favoriteRepoDao.deleteCompletable(favoriteRepoEntity)
    }

}