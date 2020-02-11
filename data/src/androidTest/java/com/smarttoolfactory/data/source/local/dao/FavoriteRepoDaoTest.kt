package com.smarttoolfactory.data.source.local.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.smarttoolfactory.data.factory.TestObjectFactory
import com.smarttoolfactory.data.model.local.FavoriteRepoEntity
import com.smarttoolfactory.data.source.local.AppDatabase
import io.reactivex.observers.TestObserver
import org.junit.*
import org.junit.runner.RunWith


/**
 *
 */
/*
https://medium.com/androiddevelopers/room-rxjava-acb0cd4f3757
 */
@RunWith(AndroidJUnit4::class)
class FavoriteRepoDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase

    private lateinit var favoriteRepoDao: FavoriteRepoDao

    @Before
    fun setUp() {

        // using an in-memory database because the information stored here disappears after test
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), AppDatabase::class.java
        )
            // allowing main thread queries, just for testing
            .allowMainThreadQueries()
            .build()

        favoriteRepoDao = database.favoriteRepoDao()

    }

    @After
    @Throws(Exception::class)
    fun closeDb() {
        database.close()
    }


    @Test
    fun shouldSaveSelectedRepoToFavorites() {

        // GIVEN
        val testObserver = TestObserver<List<FavoriteRepoEntity>>()
        val favoriteRepoEntity = TestObjectFactory.getMockFavoriteRepoEntity()

        // WHEN
        val id = favoriteRepoDao.insertMaybe(favoriteRepoEntity).blockingGet().toInt()
        favoriteRepoDao.getFavoriteRepos().subscribe(testObserver)

        // THEN
        // Assert that subscribed
        testObserver.assertSubscribed()

        //🔥🔥 Block and wait for Observable to terminate
        testObserver.awaitTerminalEvent()

        //Assert TestObserver called onComplete()
        testObserver.assertComplete()

        //Assert there were no errors
        testObserver.assertNoErrors()
        val actual = testObserver.values()[0][0]
        Assert.assertEquals(id, actual.repoId)

        // Dispose
        testObserver.dispose()

    }

    @Test
    fun shouldDeleteSelectedRepoFromFavorites() {

        // GIVEN
        val favoriteRepoEntity = TestObjectFactory.getMockFavoriteRepoEntity()
        favoriteRepoDao.insertCompletable(favoriteRepoEntity).blockingAwait()


        // WHEN
        favoriteRepoDao.deleteCompletable(favoriteRepoEntity).blockingGet()

        // THEN
        favoriteRepoDao.getFavoriteRepos()
            .test()
            // check that there's no repo emitted
            .assertValue {
                it.isEmpty()
            }
            .dispose()
    }

    @Test
    fun shouldReturnFavoriteReposOnlyForSelectedUser() {

        // GIVEN
        val ownerId = 35650605
        val repoId = 169870520

        /**
         *  Try to add times where 2 different users with 3 unique repos
         *
         *  Db should return 2 repos for selected user and 3 for query without no user
         */

        val favoriteRepoEntity1 =
            TestObjectFactory.getMockFavoriteRepoEntity(ownerId, repoId, "Test")
        val favoriteRepoEntity2 =
            TestObjectFactory.getMockFavoriteRepoEntity(ownerId, repoId, "Dagger")
        val favoriteRepoEntity3 =
            TestObjectFactory.getMockFavoriteRepoEntity(ownerId, 1, "Unknown")
        val favoriteRepoEntity4 =
            TestObjectFactory.getMockFavoriteRepoEntity(2, 2, "Unkown2")

        val list = listOf(
            favoriteRepoEntity1,
            favoriteRepoEntity2,
            favoriteRepoEntity3,
            favoriteRepoEntity4
        )
        favoriteRepoDao.insertCompletable(list)
            .doOnError {
                println("onError: ${it.message}")
            }
            .doOnComplete {
                println("doOnComplete")
            }
            .blockingAwait()

        // WHEN
        favoriteRepoDao.getFavoriteRepos()
            .test()
            // check that there are 3 favorite repos in dv
            .assertValue {
                it.size == 3
            }
            .dispose()

        // THEN
        favoriteRepoDao.getFavoriteReposByOwnerId(ownerId)
            .test()
            // check there are 2 favorite repos belong to user with selected id
            .assertValue {
                it.size == 2
            }
            .dispose()


    }

}