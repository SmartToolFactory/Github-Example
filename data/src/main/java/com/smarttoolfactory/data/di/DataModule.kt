package com.smarttoolfactory.data.di

import com.smarttoolfactory.data.api.GithubApi
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
    fun providesRemoteGithubDataSource(githubApi: GithubApi): GithubDataSource =
        RemoteGithubDataSource(githubApi)

    @Singleton
    @Provides
    @Named("local")
    fun providesLocalGithubDataSource(
        repoDao: RepoDao,
        favoriteRepoDao: FavoriteRepoDao
    ): GithubDataSource = LocalGithubDataSource(repoDao, favoriteRepoDao)

    /*
        Repository
     */

    @Singleton
    @Provides
    fun provideOrdersRepository(
        @Named("remote") webService: GithubDataSource,
        @Named("local") localOrdersDataSource: GithubDataSource
    ): GithubRepository {
        return RepositoryImpl(webService, localOrdersDataSource)
    }

}