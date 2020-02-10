package com.smarttoolfactory.data.repository

import com.smarttoolfactory.data.mapper.Mapper
import com.smarttoolfactory.data.model.local.FavoriteRepoEntity
import com.smarttoolfactory.data.model.local.RepoEntity
import com.smarttoolfactory.data.model.remote.response.RepoDTO
import com.smarttoolfactory.data.source.GithubDataSource
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val webService: GithubDataSource,
    private val localDataSource: GithubDataSource,
    private val mapperDTOtoEntity: Mapper<RepoDTO, RepoEntity>,
    private val mapperToFavorite: Mapper<RepoEntity, FavoriteRepoEntity>
) : GithubRepository {


}