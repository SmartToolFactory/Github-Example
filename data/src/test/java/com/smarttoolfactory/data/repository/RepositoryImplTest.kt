package com.smarttoolfactory.data.repository

import com.smarttoolfactory.data.mapper.Mapper
import com.smarttoolfactory.data.model.local.FavoriteRepoEntity
import com.smarttoolfactory.data.model.local.RepoEntity
import com.smarttoolfactory.data.model.remote.response.RepoDTO
import com.smarttoolfactory.data.source.local.LocalGithubDataSource
import com.smarttoolfactory.data.source.remote.RemoteGithubDataSource
import io.mockk.clearMocks
import io.mockk.mockk
import io.reactivex.observers.TestObserver
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance


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

     Scenario 3- Web Service returns 404 or 500 error and DB does not have any daya
    1- Given Exception returned from web service check DB
    2- Given DB has no data for current user return empty list

 */


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RepositoryImplTest {

    private val localDataSource: LocalGithubDataSource = mockk()
    private val remoteDataSource: RemoteGithubDataSource = mockk()
    private val mapperDTOtoEntity: Mapper<RepoDTO, RepoEntity> = mockk()
    private val mapperToFavorite: Mapper<RepoEntity, FavoriteRepoEntity> = mockk()

    private lateinit var repository: GithubRepository

    private lateinit var testObserver: TestObserver<List<RepoDTO>>

    @BeforeEach
    fun setUp() {
        testObserver = TestObserver()
    }

    @AfterEach
    fun tearDown() {

        clearMocks(localDataSource)
        clearMocks(remoteDataSource)

        repository = RepositoryImpl(
            localDataSource, remoteDataSource,
            mapperDTOtoEntity, mapperToFavorite
        )

        testObserver.dispose()
    }


}