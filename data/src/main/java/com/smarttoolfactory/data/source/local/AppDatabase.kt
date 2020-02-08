package com.smarttoolfactory.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.smarttoolfactory.data.model.local.FavoriteRepoEntity
import com.smarttoolfactory.data.model.local.RepoEntity
import com.smarttoolfactory.data.source.local.dao.RepoDao
import com.smarttoolfactory.data.source.local.typeconvertors.RoomTypeConverter

/**
 * Room Database that contains the [RepoEntity] entity tables.
 *
 */
@Database(
    entities = [RepoEntity::class, FavoriteRepoEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(RoomTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun repoDao(): RepoDao
}

