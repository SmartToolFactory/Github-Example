package com.smarttoolfactory.data.source.local.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.smarttoolfactory.data.factory.TestObjectFactory
import com.smarttoolfactory.data.model.local.RepoEntity
import com.smarttoolfactory.data.source.local.AppDatabase
import com.smarttoolfactory.data.utils.logLifeCycleEvents
import io.reactivex.observers.TestObserver
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.security.MessageDigest


@RunWith(AndroidJUnit4::class)
class RepoDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase

    private lateinit var repoDao: RepoDao

    @Before
    fun setUp() {

        // using an in-memory database because the information stored here disappears after test
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), AppDatabase::class.java
        )
            // allowing main thread queries, just for testing
            .allowMainThreadQueries()
            .build()

        repoDao = database.repoDao()

    }

    @After
    @Throws(Exception::class)
    fun closeDb() {
        database.close()
    }

    @Test
    fun givenEmptyShouldReturnNoValues() {

        repoDao.getRepos()
            .test()
            .assertValue {
                it.isEmpty()
            }
            .dispose()

    }

    @Test
    fun shouldInsertAndReturnRepoId() {


        // GIVEN
        val testObserver = TestObserver<List<RepoEntity>>()
        val repoEntity = TestObjectFactory.getMockRepoEntity(false)

        // WHEN
        val id = repoDao.insertMaybe(repoEntity).blockingGet().toInt()
        repoDao.getRepos().subscribe(testObserver)

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
        assertEquals(id, actual.repoId)

        // Dispose
        testObserver.dispose()
    }

    @Test
    fun shouldInsertAndReturnRepoList() {

        // GIVEN
        val repoEntity = TestObjectFactory.getMockRepoEntity(false)

        // WHEN
        repoDao.insertCompletable(repoEntity).blockingAwait()

        // THEN
        repoDao.getRepos()
            .test()
            .assertValue {
                it.size == 1 && it.contains(repoEntity)
            }
            .dispose()

    }


    @Test
    fun shouldDeleteRepoListAndReturnEmptyList() {

        // GIVEN
        val repoEntity = TestObjectFactory.getMockRepoEntity(false)
        repoDao.insertCompletable(repoEntity).blockingAwait()

        // WHEN
        repoDao.deleteAllCompletable().blockingGet()

        // THEN
        repoDao.getReposSingle()
            .logLifeCycleEvents()
            .test()
            // check that there's no repo emitted
            .assertValue {
                it.isEmpty()
            }
            .dispose()
    }

    @Test
    fun shouldUpdateFavoriteStatusOfRepo() {
        // GIVEN
        val repoEntity = TestObjectFactory.getMockRepoEntity(false)
        repoDao.insertCompletable(repoEntity).blockingAwait()

        repoEntity.isFavorite = true

        // WHEN
        repoDao.updateCompletable(repoEntity).blockingGet()

        repoDao.getRepos()
            .test()
            // check that there's no repo emitted
            .assertValue {
                it[0].isFavorite
            }
            .dispose()

    }

    @Test
    fun test() {
        val md = MessageDigest.getInstance("SHA-256")
    }

}