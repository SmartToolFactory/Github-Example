package com.smarttoolfactory.data.source.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.smarttoolfactory.data.model.local.FavoriteRepoEntity
import io.reactivex.Observable

@Dao
interface FavoriteRepoDao : BaseDao<FavoriteRepoEntity> {


    /**
     * Get list of favorite repos to from database
     */
    @Query("SELECT * FROM favorite WHERE login =:user")
    fun getFavoriteReposByUser(user: String): Observable<List<FavoriteRepoEntity>>


    @Query("SELECT * FROM favorite")
    fun getFavoriteRepos(): Observable<List<FavoriteRepoEntity>>

}