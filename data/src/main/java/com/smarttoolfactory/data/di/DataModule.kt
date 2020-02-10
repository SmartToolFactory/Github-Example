package com.smarttoolfactory.data.di

import com.smarttoolfactory.data.api.GithubApi
import com.smarttoolfactory.data.mapper.Mapper
import com.smarttoolfactory.data.model.local.FavoriteRepoEntity
import com.smarttoolfactory.data.model.local.RepoEntity
import com.smarttoolfactory.data.model.remote.response.RepoDTO
import com.smarttoolfactory.data.repository.GithubRepository
import com.smarttoolfactory.data.repository.RepositoryImpl
import com.smarttoolfactory.data.source.GithubDataSource
import com.smarttoolfactory.data.source.local.LocalGithubDataSource
import com.smarttoolfactory.data.source.local.dao.FavoriteRepoDao
import com.smarttoolfactory.data.source.local.dao.RepoDao
import com.smarttoolfactory.data.source.remote.RemoteGithubDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module(includes = [NetworkModule::class, DatabaseModule::class])
class DataModule {

    /*
        Data Sources
     */

    @Singleton
    @Provides
    @Named("remote")
    fun provideRemoteGithubDataSource(githubApi: GithubApi): GithubDataSource =
        RemoteGithubDataSource(githubApi)

    @Singleton
    @Provides
    @Named("local")
    fun provideLocalGithubDataSource(
        repoDao: RepoDao,
        favoriteRepoDao: FavoriteRepoDao
    ): GithubDataSource = LocalGithubDataSource(repoDao, favoriteRepoDao)

    /*
        Repository
     */

    @Singleton
    @Provides
    @Named("RepoDTOtoEntity")
    fun provideRepoDTOtoEntityMapper(): Mapper<RepoDTO, RepoEntity> {

        return object : Mapper<RepoDTO, RepoEntity> {

            override fun map(input: RepoDTO): RepoEntity {

                return RepoEntity(
                    repoId = input.repoId,
                    repoName = input.name,
                    starCount = input.stars,
                    openIssuesCount = input.issueCount,
                    ownerId = input.owner.ownerId,
                    login = input.owner.login,
                    avatarUrl = input.owner.avatarUrl,
                    isFavorite = false
                )
            }
        }
    }
    @Singleton
    @Provides
    @Named("RepoToFavoriteEntity")
    fun provideRepoToFavoriteEntityMapper(): Mapper<RepoEntity, FavoriteRepoEntity> {

        return object : Mapper<RepoEntity, FavoriteRepoEntity> {

            override fun map(input: RepoEntity): FavoriteRepoEntity {

                return FavoriteRepoEntity(
                    repoId = input.repoId,
                    repoName = input.repoName,
                    starCount = input.starCount,
                    openIssuesCount = input.openIssuesCount,
                    ownerId = input.ownerId,
                    ownerName = input.login,
                    ownerAvatarUrl = input.avatarUrl
                )
            }
        }
    }

    @Singleton
    @Provides
    fun provideRepoRepository(
        @Named("remote") webService: GithubDataSource,
        @Named("local") localGithubDataSource: GithubDataSource,
        @Named("RepoDTOtoEntity") mapperRepoDTOtoEntity: Mapper<RepoDTO, RepoEntity>,
        @Named("RepoToFavoriteEntity") mapperRepoToFavoriteEntityMapper: Mapper<RepoEntity, FavoriteRepoEntity>

    ): GithubRepository {
        return RepositoryImpl(
            webService,
            localGithubDataSource,
            mapperRepoDTOtoEntity,
            mapperRepoToFavoriteEntityMapper
        )
    }

}