package com.smarttoolfactory.data.source.local.dao


import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import io.reactivex.Maybe

/**
 * Base Data Access Object interface for generic currencyType T. Contains methods for common functions that does not include
 * queries with @Query
 * @param T generic currencyType of Object to be inserted, updated or deleted
</T> */
@Dao
interface BaseDao<T> {


    @Insert(onConflict = REPLACE)
    fun insert(entity: T): Maybe<Long>


    @Update
    fun update(entity: T): Maybe<Int>

    @Delete
    fun delete(entity: T): Maybe<Int>

}
