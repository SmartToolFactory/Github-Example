package com.smarttoolfactory.data.source.remote

import com.smarttoolfactory.data.api.GithubApi
import com.smarttoolfactory.data.factory.TestObjectFactory
import com.smarttoolfactory.data.model.remote.response.RepoDTO
import io.mockk.*
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.Schedulers
import org.junit.jupiter.api.*


class RemoteGithubDataSourceTest {

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class MockRemoteTest {

        /**
         * Mocked github api for mocked tests
         */
        private val webService: GithubApi = mockk()

        private lateinit var remoteDataSource: RemoteGithubDataSource

        private lateinit var testObserver: TestObserver<List<RepoDTO>>

        @BeforeEach
        fun setUp() {
            remoteDataSource = RemoteGithubDataSource(webService)
            testObserver = TestObserver()
        }

        @AfterEach
        fun tearDown() {
            clearMocks(webService)
            testObserver.dispose()
        }


        @Test
        fun `Should return repo DTOs given user param is valid`() {

            // GIVEN
            val user = "smarttoolfactory"
            val listDTO = listOf(TestObjectFactory.getMockRepoDTO())

            every { webService.getRepoList(user) } returns Observable.just(listDTO)

            //Assert no subscription has occurred yet
            testObserver.assertNotSubscribed()

            // WHEN
            //Subscribe TestObserver to source
            remoteDataSource.getRepoDTOs(user)
                .subscribeOn(Schedulers.io())
                .subscribe(testObserver)

            // THEN
            //Assert TestObserver is subscribed
            testObserver.assertSubscribed()

            //🔥🔥 Block and wait for Observable to terminate, Useful for testing with Schedulers
            testObserver.awaitTerminalEvent()

            // Assert that no errors
            testObserver.assertNoErrors()

            //Assert TestObserver called onComplete()
            testObserver.assertComplete()

            val item = testObserver.values()[0]

            verify(exactly = 1) { webService.getRepoList(user) }
            confirmVerified(webService)

        }

        @Test
        fun `Should return 404 error given user param is invalid`() {

            // GIVEN
            val user = "smarttoolfactory"
            every {
                webService.getRepoList(any())
            } returns Observable.error(Exception("404 error"))

            val testObserver = TestObserver<List<RepoDTO>>()

            // WHEN
            remoteDataSource.getRepoDTOs(user)
                .subscribeOn(Schedulers.io())
                .subscribe(testObserver)

            // THEN
            // Subscribes here
            //Assert TestObserver is subscribed
            testObserver.assertSubscribed()

            //🔥🔥 Block and wait for Observable to terminate, Useful for testing with Schedulers
            testObserver.awaitTerminalEvent()

            // Assert that throws HttpException
            testObserver.assertError(Exception::class.java)

            //Assert TestObserver DIDN'T call onComplete()
            testObserver.assertNotComplete()

            // Asset TesObserver DIDN'T call onTerminate()
            testObserver.assertTerminated()

            verify(exactly = 1) { webService.getRepoList(any()) }
            confirmVerified(webService)

        }
    }

    /**
     * Tests with MockWebServer as stub for web service
     */
    @Nested
    inner class StubRemoteTest {

        @Test
        fun `Should return repo entities given user param is valid`() {

        }

        @Test
        fun `Should return 404 HttpException given user param is invalid`() {

        }

    }


}