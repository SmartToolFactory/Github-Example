package com.smarttoolfactory.data.source.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.smarttoolfactory.data.model.local.RepoEntity
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Data Access Object for the rates table.
 */
@Dao
interface RepoDao : BaseDao<RepoEntity> {


    /**
     * Get list of repos to from database
     */
    @Query("SELECT * FROM repo WHERE  login =:user")
    fun getRepos(user: String): Single<List<RepoEntity>>

    @Query("DELETE FROM repo")
    fun deleteAll(): Completable
}