package com.smarttoolfactory.data.source.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.smarttoolfactory.data.model.local.RepoEntity
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Data Access Object for the rates table.
 */
@Dao
interface RepoDao : BaseRxDao<RepoEntity> {

    /**
     * Get list of repos to from database that belong to this user
     *
     *  Single will trigger onError(EmptyResultSetException.class) when there are no elements in DB
     */
    @Query("SELECT * FROM repo WHERE  login =:user")
    fun getReposSingle(user: String): Single<List<RepoEntity>>

    /**
     * Get list of repos to from database as Single.
     *
     *  Single will trigger onError(EmptyResultSetException.class) when there are no elements in DB
     */
    @Query("SELECT * FROM repo")
    fun getReposSingle(): Single<List<RepoEntity>?>



    /**
     * Get list of repos to from database that belong to this user
     *
     * Maybe will only call onComplete() when there ano elements in DB
     */
    @Query("SELECT * FROM repo WHERE  login =:user")
    fun getReposMaybe(user: String): Maybe<List<RepoEntity>>

    /**
     * Get list of repos to from database.
     */
    @Query("SELECT * FROM repo")
    fun getReposMaybe(): Maybe<List<RepoEntity>>

    /**
     * Get list of repos to from database that belong to this user
     */
    @Query("SELECT * FROM repo WHERE  login =:user")
    fun getRepos(user: String): Observable<List<RepoEntity>>

    /**
     * Get list of repos to from database.
     */
    @Query("SELECT * FROM repo")
    fun getRepos(): Observable<List<RepoEntity>>

    @Query("DELETE FROM repo")
    fun deleteAllCompletable(): Completable


}