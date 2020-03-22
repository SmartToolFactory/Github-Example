package com.smarttoolfactory.data.di

import com.smarttoolfactory.data.mapper.Mapper
import com.smarttoolfactory.data.model.local.FavoriteRepoEntity
import com.smarttoolfactory.data.model.local.RepoEntity
import com.smarttoolfactory.data.model.remote.response.RepoDTO
import com.smarttoolfactory.data.repository.GithubRepository
import com.smarttoolfactory.data.repository.RepositoryImpl
import com.smarttoolfactory.data.source.GithubDataSource
import com.smarttoolfactory.data.source.local.LocalGithubDataSource
import com.smarttoolfactory.data.source.remote.RemoteGithubDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module(includes = [DataProviderModule::class, NetworkModule::class, DatabaseModule::class])
interface DataModule {

    /*
        Data Sources
     */
    @Singleton
    @Binds
    @Named("remote")
    fun bindRemoteGithubDataSource(remoteGithubDataSource: RemoteGithubDataSource): GithubDataSource

    @Singleton
    @Binds
    @Named("local")
    fun bindLocalGithubDataSource(localGithubDataSource: LocalGithubDataSource): GithubDataSource

    /*
        Repository
    */
    @Singleton
    @Binds
    fun bindRepository(repositoryImpl: RepositoryImpl): GithubRepository

}

@Module
object DataProviderModule {

    @JvmStatic
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

    @JvmStatic
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

}