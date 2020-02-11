package com.smarttoolfactory.data.repository

import com.smarttoolfactory.data.mapper.Mapper
import com.smarttoolfactory.data.model.local.FavoriteRepoEntity
import com.smarttoolfactory.data.model.local.RepoEntity
import com.smarttoolfactory.data.model.remote.response.RepoDTO
import com.smarttoolfactory.data.source.GithubDataSource
import io.reactivex.Observable
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val webService: GithubDataSource,
    private val localDataSource: GithubDataSource,
    private val mapperDTOtoEntity: Mapper<RepoDTO, RepoEntity>,
    private val mapperToFavorite: Mapper<RepoEntity, FavoriteRepoEntity>
) : GithubRepository {

    /**
     * Gets user repos as DTO objects from web service operating with Online-First approach.
     *
     * In successful scenario it gets [RepoDTO]s from web service maps them to [RepoEntity]
     * and saves them to database to use db as Single Source of Truth.
     *
     * If there occurs an exception it moves to onResumeNext and returns an empty list
     */
    override fun getUserReposOnlineFirst(user: String): Observable<List<RepoEntity>> {


        return webService.getRepoDTOs(user)
            .onErrorResumeNext { _: Throwable ->
                Observable.just(listOf())
            }
            .map {
                mapDTOtoEntity(it)
            }

            .flatMap {
                if (it.isNullOrEmpty()) {
                    localDataSource.getRepoEntities()
                }else {
                    // Delete previous data on db, replace with new one and return this
                    localDataSource.deleteRepos()
                        .andThen(localDataSource.saveRepoEntities(it))
                        .andThen(Observable.just(it))
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