package com.smarttoolfactory.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.smarttoolfactory.data.constant.DATABASE_VERSION
import com.smarttoolfactory.data.model.local.FavoriteRepoEntity
import com.smarttoolfactory.data.model.local.RepoEntity
import com.smarttoolfactory.data.source.local.dao.FavoriteRepoDao
import com.smarttoolfactory.data.source.local.dao.RepoDao

/**
 * Room Database that contains the [RepoEntity], and [FavoriteRepoEntity] entity tables.
 *
 */
@Database(
    entities = [RepoEntity::class, FavoriteRepoEntity::class],
    version = DATABASE_VERSION,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun repoDao(): RepoDao

    abstract fun favoriteRepoDao(): FavoriteRepoDao

}

