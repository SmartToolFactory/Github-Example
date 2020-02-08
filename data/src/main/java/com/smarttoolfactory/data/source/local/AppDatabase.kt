package com.smarttoolfactory.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.smarttoolfactory.data.model.local.RepoEntity
import com.smarttoolfactory.data.source.local.dao.RepoDao

/**
 * Room Database that contains the [RepoEntity] entity tables.
 *
 */
@Database(entities = [RepoEntity::class], version = 1, exportSchema = false)

abstract class AppDatabase : RoomDatabase() {

    abstract fun repoDao(): RepoDao
}

