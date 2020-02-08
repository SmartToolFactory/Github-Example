package com.smarttoolfactory.data.di

import android.app.Application
import androidx.room.Room
import com.smarttoolfactory.data.constant.DATABASE_NAME
import com.smarttoolfactory.data.source.local.AppDatabase
import com.smarttoolfactory.data.source.local.dao.FavoriteRepoDao
import com.smarttoolfactory.data.source.local.dao.RepoDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideRepoDao(appDatabase: AppDatabase): RepoDao {
        return appDatabase.repoDao()
    }

    @Singleton
    @Provides
    fun provideFavoriteRepoDao(appDatabase: AppDatabase): FavoriteRepoDao {
        return appDatabase.favoriteRepoDao()
    }


}