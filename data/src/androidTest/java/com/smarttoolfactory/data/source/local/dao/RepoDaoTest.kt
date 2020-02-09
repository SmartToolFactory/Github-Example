package com.smarttoolfactory.data.source.local.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.smarttoolfactory.data.factory.TestObjectFactory
import com.smarttoolfactory.data.source.local.AppDatabase
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


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
    fun shouldInsertAndReturnRepoList() {

        // GIVEN
        val repoEntity = TestObjectFactory.getMockRepoEntity(false)

        // WHEN
//       val id = repoDao.insert(repoEntity).blockingGet()

        // THEN

    }

    @Test
    fun shouldDeleteRepoListAndReturnEmpty() {

    }

    @Test
    fun shouldUpdateFavoriteStatusOfRepo() {

    }

}