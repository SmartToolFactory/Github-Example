package com.smarttoolfactory.data.repository

import com.google.common.truth.Truth.assertThat
import com.smarttoolfactory.data.factory.TestObjectFactory
import com.smarttoolfactory.data.mapper.Mapper
import com.smarttoolfactory.data.model.local.FavoriteRepoEntity
import com.smarttoolfactory.data.model.local.RepoEntity
import com.smarttoolfactory.data.model.remote.response.RepoDTO
import com.smarttoolfactory.data.source.GithubDataSource
import io.mockk.*
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.Schedulers
import org.junit.jupiter.api.*

/**
This test class uses Truth assertion framework
 */

/**

 * Repository test for getting RepoDTO's, storing them on DB and fetching them when required,
 * and deleting old that whenever new data is retrieved.
 *
 * There are 2 approaches to use Single Source of Truth
 * 1- Online-first: Check Web Service for data first, if there is an error occurs check for DB
 * 2- Offline-first: Check DB for data first, if it's empty fetch data from Web Service
 *
 */

/*

    Feature 1: Get Repo DTOs and return them as entities with online-first fashion

    Scenario 1- Success
    1- Fetch RepoDTOs from web service
    2- Map DTO to Entity
    3- Given data is retrieved save delete previous data
    4- Save current data to DB
    5- Retrieve data

    Scenario 2- Web Service returns 404 or 500 error but DB has same user data
    1- Given Exception returned from web service check DB
    2- Given data is available retrieve data from DB

     Scenario 3- Web Service returns 404 or 500 error and DB does not have any data
    1- Given Exception returned from web service check DB
    2- Given DB has no data for current user return empty list

 */


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RepositoryImplTest {

    private val localDataSource: GithubDataSource = mockk()
    private val remoteDataSource: GithubDataSource = mockk()
    private val mapperDTOtoEntity: Mapper<RepoDTO, RepoEntity> = mockk()
    private val mapperToFavorite: Mapper<RepoEntity, FavoriteRepoEntity> = mockk()

    private lateinit var repository: RepositoryImpl

    private lateinit var testObserver: TestObserver<List<RepoEntity>>


    @Nested
    @DisplayName("Search and Fetch Repo Tests")
    inner class SearchRepoTests {


        @Test
        fun `Given DTOs returned from web service, fetch should return mapped repo entity list`() {

            // GIVEN
            val user = "smarttoolfactory"

            val repoDTO = TestObjectFactory.getMockRepoDTO()
            val repoDTOList = listOf(repoDTO)

            val repoEntity = TestObjectFactory.getMockRepoEntity()

            every { remoteDataSource.getRepoDTOs(user) } returns Observable.just(repoDTOList)
            every { mapperDTOtoEntity.map(repoDTO) } returns repoEntity

            // WHEN
            repository
                .fetchRepoEntity(user)
                .subscribe(testObserver)


            // THEN
            //Assert TestObserver is subscribed
            testObserver.assertSubscribed()

            //🔥🔥 Block and wait for Observable to terminate, Useful for testing with Schedulers
            testObserver.awaitTerminalEvent()


            val actual = testObserver.values()[0][0]

            assertThat(actual).isEqualTo(repoEntity)

            // Verify that repository retrieved items once and mapped one item only once
            verify(exactly = 1) { remoteDataSource.getRepoDTOs(user) }
            verify(exactly = 1) { mapperDTOtoEntity.map(repoDTO) }

        }


        @Test
        fun `Given exception returned from web service, fetch should return an empty repo entity list`() {

            // GIVEN
            val user = "smarttoolfactory"

            // Repository tries to fetch from web but gets an exception
            every { remoteDataSource.getRepoDTOs(user) } returns Observable.error(Exception())
            every { mapperDTOtoEntity.map(any()) }

            // WHEN
            repository.fetchRepoEntity(user)
                // THEN
                .test()
                .assertValue {
                    it.isEmpty()
                }
                .dispose()


            // Verify that repository tried to fetch but map is not called
            verify(exactly = 1) { remoteDataSource.getRepoDTOs(user) }
            verify(exactly = 0) { mapperDTOtoEntity.map(any()) }

        }

        @Test
        fun `Given web service returned empty list mapper should not be called`() {

            // GIVEN
            val user = "smarttoolfactory"

            // Repository tries to fetch from web but gets an exception
            every { remoteDataSource.getRepoDTOs(user) } returns Observable.just(listOf())
            every { mapperDTOtoEntity.map(any()) }

            // WHEN
            repository.fetchRepoEntity(user)
                // THEN
                .test()
                .dispose()

            // Verify that repository fetched empty repo list  but map is not called
            verify(exactly = 1) { remoteDataSource.getRepoDTOs(user) }
            verify(exactly = 0) { mapperDTOtoEntity.map(any()) }
        }

        @Test
        fun `Given repo entity list returned, should save entities to DB`() {

            // GIVEN
            val user = "smarttoolfactory"

            val repoDTO = TestObjectFactory.getMockRepoDTO()
            val repoDTOList = listOf(repoDTO)

            val repoEntity = TestObjectFactory.getMockRepoEntity()
            val repoEntityList = listOf(repoEntity)

            // Fetch and map DTO into Entity
            every { remoteDataSource.getRepoDTOs(user) } returns Observable.just(repoDTOList)
            every { mapperDTOtoEntity.map(repoDTO) } returns repoEntity

            // Delete previous data and save current one
            every { localDataSource.deleteRepos() } returns Completable.complete()
            every { localDataSource.saveRepoEntities(repoEntityList) } returns Completable.complete()

            // These function is not called
            every { localDataSource.getRepoEntitiesByUser(user) }

            // WHEN
            repository.getUserReposOnlineFirst(user)
                .subscribeOn(Schedulers.io())
                .subscribe(testObserver)

            // 🔥 Schedulers.io() thread should be waited
            testObserver.awaitTerminalEvent()

            // THEN
            // Verify that these methods are not called
            verifyAll {

                remoteDataSource.getRepoDTOs(user)
                mapperDTOtoEntity.map(any())

                // Delete previous data and save current one
                localDataSource.deleteRepos()
                localDataSource.saveRepoEntities(repoEntityList)

                localDataSource.getRepoEntitiesByUser(user) wasNot Called
            }

        }

        @Test
        fun `Given empty repo entity list returned, should fetch data from DB`() {

            // GIVEN
            val user = "smarttoolfactory"

            val repoEntity = TestObjectFactory.getMockRepoEntity()
            val repoEntityList = listOf(repoEntity)

            // Fetch fails so get data from db
            every { remoteDataSource.getRepoDTOs(user) } returns Observable.error(Exception())
            every { localDataSource.getRepoEntitiesByUser(user) } returns Observable.just(
                repoEntityList
            )

            // Should not be called
            every { mapperDTOtoEntity.map(any()) }
//            every { localDataSource.deleteRepos() } returns Completable.complete()
            every { localDataSource.saveRepoEntities(repoEntityList) } returns Completable.complete()


            // WHEN
            repository.getUserReposOnlineFirst(user)
                .subscribeOn(Schedulers.io())
                .subscribe(testObserver)

            // 🔥 Schedulers.io() thread should be waited
            testObserver.awaitTerminalEvent()


            // THEN
            val actual = testObserver.values()[0]
            assertThat(actual).isEqualTo(repoEntityList)

            // Verify that these methods are not called
            verifyAll {

                remoteDataSource.getRepoDTOs(user)
                localDataSource.getRepoEntitiesByUser(user)
                mapperDTOtoEntity.map(any()) wasNot Called

                // Delete previous data and save current one
                localDataSource.deleteRepos() wasNot Called
                localDataSource.saveRepoEntities(repoEntityList) wasNot Called

            }
        }

        @Test
        fun `Given both web and DB is empty should return a empty entity list`() {

            // GIVEN
            val user = "smarttoolfactory"

            // Fetch fails so get data from db
            every { remoteDataSource.getRepoDTOs(user) } returns Observable.error(Exception())
            every { localDataSource.getRepoEntitiesByUser(user) } returns Observable.just(listOf())

            // Should not be called
            every { mapperDTOtoEntity.map(any()) }
            every { localDataSource.deleteRepos() } returns Completable.complete()
            every { localDataSource.saveRepoEntities(any()) } returns Completable.complete()

            // WHEN
            val testObserver = repository.getUserReposOnlineFirst(user)
                .subscribeOn(Schedulers.io())
                // THEN
                .test()

            // 🔥 Schedulers.io() thread should be waited
            testObserver.awaitTerminalEvent()

            testObserver.dispose()

            // THEN
            // Verify that these methods are not called
            verifyAll {

                remoteDataSource.getRepoDTOs(user)
                localDataSource.getRepoEntitiesByUser(user)
                mapperDTOtoEntity.map(any()) wasNot Called

                // Delete previous data and save current one
                localDataSource.deleteRepos() wasNot Called
                localDataSource.saveRepoEntities(any()) wasNot Called

            }
        }
    }

    @BeforeEach
    fun setUp() {

        testObserver = TestObserver()

        repository = RepositoryImpl(
            remoteDataSource, localDataSource,
            mapperDTOtoEntity, mapperToFavorite
        )
    }

    @AfterEach
    fun tearDown() {

        clearMocks(localDataSource)
        clearMocks(remoteDataSource)

        testObserver.dispose()
    }


}