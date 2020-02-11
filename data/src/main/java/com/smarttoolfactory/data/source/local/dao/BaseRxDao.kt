package com.smarttoolfactory.data.source.local.dao


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Update
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single


/*
    https://medium.com/androiddevelopers/room-rxjava-acb0cd4f3757
 */
/**
 * Base Data Access Object interface for generic T. Contains methods for common functions that does not include
 * queries with @Query
 * @param T generic type of Object to be inserted, updated or deleted
</T> */
@Dao
interface BaseRxDao<T> {

    /*
        In case of error inserting the data, Completable, Single and Maybe will emit the exception in onError.
     */

    /*
        *** QUERY ***
        To get the user from the database, we could write the following query in the data access object class (UserDao):

        @Query(“SELECT * FROM Users WHERE id = :userId”)
        User getUserById(String userId);

        This approach has two disadvantages:
        It is a blocking, synchronous call
        We need to manually call this method every time our user data is modified
        Room provides the option of observing the data in the database and performing asynchronous
        queries with the help of RxJava Maybe, Single and Flowable objects.
        If you’re worried about threads, Room keeps you at ease and ensures
        that observable queries are done off the main thread.
        It’s up to you to decide on which thread the events are emitted downstream,
        by setting the Scheduler in the observeOn method.
        For queries that return Maybe or Single, make sure you’re calling subscribeOn
        with a different Scheduler than AndroidSchedulers.mainThread().

        To start using Room with RxJava 2, just add the following dependencies to your build.gradle file:
        // RxJava support for Room
        implementation “android.arch.persistence.room:rxjava2:1.0.0-alpha5”
        // Testing support
        androidTestImplementation “android.arch.core:core-testing:1.0.0-alpha5”

        Maybe
        @Query(“SELECT * FROM Users WHERE id = :userId”)
        Maybe<User> getUserById(String userId);
        Here’s what happens:
        1- When there is no user in the database and the query returns no rows, Maybe will complete.
        2- When there is a user in the database, Maybe will trigger onSuccess and it will complete.
        3- If the user is updated after Maybe was completed, nothing happens.

        Single
        @Query(“SELECT * FROM Users WHERE id = :userId”)
        Single<User> getUserById(String userId);
        Here are some scenarios:
        1- When there is no user in the database and the query returns no rows,
        🔥 Single will trigger onError(EmptyResultSetException.class)
        2- When there is a user in the database, Single will trigger onSuccess.
        3- If the user is updated after Single was completed, nothing happens.
     */

    /*
        Insert Single entity
     */
    @Insert(onConflict = REPLACE)
    fun insertMaybe(entity: T): Maybe<Long>

    @Insert(onConflict = REPLACE)
    fun insertSingle(entity: T): Single<Long>

    @Insert(onConflict = REPLACE)
    fun insertCompletable(entity: T): Completable

    /*
        Insert Multiple entities
     */
    @Insert(onConflict = REPLACE)
    fun insertCompletable(entities: List<T>): Completable

    /*
        Update
     */
    @Update
    fun updateCompletable(entity: T): Completable

    @Update
    fun updateSingle(entity: T): Single<Int>

    @Update
    fun updateMaybe(entity: T): Maybe<Int>

    /*
        Delete
     */
    @Delete
    fun deleteCompletable(entity: T): Completable

    @Delete
    fun deleteSingle(entity: T): Single<Int>

    @Delete
    fun deleteMaybe(entity: T): Maybe<Int>
}
