package com.smarttoolfactory.data.repository

import androidx.annotation.VisibleForTesting
import com.smarttoolfactory.data.mapper.Mapper
import com.smarttoolfactory.data.model.local.FavoriteRepoEntity
import com.smarttoolfactory.data.model.local.RepoEntity
import com.smarttoolfactory.data.model.remote.response.RepoDTO
import com.smarttoolfactory.data.source.GithubDataSource
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Named

class RepositoryImpl @Inject constructor(
    @Named("remote")  private val webService: GithubDataSource,
    @Named("local")  private val localDataSource: GithubDataSource,
    @Named("RepoDTOtoEntity") private val mapperDTOtoEntity: Mapper<RepoDTO, RepoEntity>,
    @Named("RepoToFavoriteEntity") private val mapperToFavorite: Mapper<RepoEntity, FavoriteRepoEntity>
) : GithubRepository {


    /**
     * Gets user repos as DTO objects from web service operating with Online-First approach.
     *
     * In successful scenario it gets [RepoDTO]s from web service maps them to [RepoEntity]
     * and saves them to database to use db as Single Source of Truth.
     *
     * If there occurs an exception it moves to onResumeNext and returns an empty list
     *
     *
     * Note: Using onErrorResumeNext() method with empty list is required since it's still DTO
     * object while [RepoEntity] is required as output
     */
    override fun getUserReposOnlineFirst(user: String): Observable<List<RepoEntity>> {

        return fetchRepoEntity(user)

            .flatMap {
                if (it.isNullOrEmpty()) {
                    localDataSource.getRepoEntitiesByUser(user)
                } else {
                    // Delete previous data on db, replace with new one and return it
                    localDataSource.deleteRepos()
                        .andThen(localDataSource.saveRepoEntities(it))
                        .andThen(Observable.just(it))
                }
            }

    }

    @VisibleForTesting
    fun fetchRepoEntity(user: String): Observable<List<RepoEntity>> {

        return webService.getRepoDTOs(user)
            .onErrorResumeNext { _: Throwable ->
                Observable.just(listOf())
            }
            .map {
                if (it.isNullOrEmpty()) {
                    listOf<RepoEntity>()
                } else {
                    mapDTOtoEntity(it)
                }

            }
    }

    private fun mapDTOtoEntity(it: List<RepoDTO>): ArrayList<RepoEntity> {
        val list = ArrayList<RepoEntity>()
        it.forEach { repoDTO ->
            list.add(mapperDTOtoEntity.map(repoDTO))
        }

        return list
    }


}