package com.smarttoolfactory.data.api

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.smarttoolfactory.data.model.remote.response.RepoDTO
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import okio.BufferedSource
import okio.Source
import okio.buffer
import okio.source
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.io.InputStream
import java.nio.charset.StandardCharsets

class GithubApiTest {

    private lateinit var githubApi: GithubApi

    private lateinit var mockWebServer: MockWebServer

    @BeforeEach
    fun createService() {

        mockWebServer = MockWebServer()

        val okHttpClient = OkHttpClient
            .Builder()
            .build()

        githubApi = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
            .create(GithubApi::class.java)


    }

    @AfterEach
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    fun `Request should have correct url`() {

        // GIVEN
        enqueueResponse(200, "repos.json")

        // WHEN
        githubApi
            .getRepoList("smarttoolfactory")
            .blockingFirst()

        val request = mockWebServer.takeRequest()

        // THEN
        assertThat(request.path, `is`("/users/smarttoolfactory/repos"))

    }

    @Test
    fun `Service should return repo list`() {

        // GIVEN
        enqueueResponse(200, "repos.json")

        // WHEN
        val repos = githubApi
            .getRepoList("smarttoolfactory")
            .blockingFirst()

        val owner = repos[0].owner

        // THEN
        assertThat(repos.size, `is`(18))
        assertThat(owner.login, `is`("SmartToolFactory"))
        assertThat(
            owner.avatarUrl,
            `is`("https://avatars0.githubusercontent.com/u/35650605?v=4")
        )
        assertThat(owner.ownerId, `is`(35650605))

    }


    @Test
    fun `Repos should return repo list with TestObserver`() {

        // GIVEN
        enqueueResponse(200, "repos.json")

        val testObserver = TestObserver<List<RepoDTO>>()


        // WHEN
        val observable = githubApi
            .getRepoList("smarttoolfactory")


        //Assert no subscription has occurred yet
        testObserver.assertNotSubscribed()

        //Subscribe TestObserver to source
        observable.subscribe(testObserver)

        // THEN
        // Subscribes here
        //Assert TestObserver is subscribed
        testObserver.assertSubscribed()

        //🔥🔥 Block and wait for Observable to terminate
        testObserver.awaitTerminalEvent()

        //Assert TestObserver called onComplete()
        testObserver.assertComplete()

        //Assert there were no errors
        testObserver.assertNoErrors()

        //Assert 1 list is received
        testObserver.assertValueCount(1)

        val repos = testObserver.values()[0]
        assertThat(repos.size, `is`(18))

        testObserver.dispose()

    }


    /*
       HttpException is wrapped in RuntimeException by RxJava
     */
    @Test
    fun `Server down should return 500 error`() {

        // GIVEN
        mockWebServer.enqueue(MockResponse().setResponseCode(500))

        // WHEN
        val exception: RuntimeException =
            assertThrows(
                RuntimeException::class.java
            ) {

                githubApi
                    .getRepoList("smarttoolfactory")
                    .blockingFirst()

            }

        // THEN
        assertThat(
            exception.message,
            `is`("com.jakewharton.retrofit2.adapter.rxjava2.HttpException: HTTP 500 Server Error")
        )

    }

    @Test
    fun `Invalid user name should return 404 error`() {

        // GIVEN
        enqueueResponse(404, "repos.json")

        val testObserver = TestObserver<List<RepoDTO>>()


        testObserver.assertNotComplete()

        // WHEN
        val observable = githubApi
            .getRepoList("smarttoolfactory")

        observable.subscribe(testObserver)

        testObserver.awaitTerminalEvent()

        // THEN
        // Assert that throws HttpException
        testObserver.assertError(HttpException::class.java)

        // Assert that onComplete not called
        testObserver.assertNotComplete()

        testObserver.dispose()

    }


    @Throws(IOException::class)
    private fun enqueueResponse(
        code: Int = 200,
        fileName: String,
        headers: Map<String, String>? = null
    ): MockResponse {
        // Open an InputStream to read mock json body
        val source: Source = getSource(fileName)

        // Define mock response
        val mockResponse = MockResponse()
        // Set response code
        mockResponse.setResponseCode(code)

        // Set headers
        headers?.let {
            for ((key, value) in it) {
                mockResponse.addHeader(key, value)
            }
        }

        // Set body
        mockWebServer.enqueue(
            mockResponse.setBody(
                (source as BufferedSource).readString(
                    StandardCharsets.UTF_8
                )
            )
        )

        return mockResponse
    }

    private fun getSource(fileName: String): Source {
        val inputStream: InputStream = this.javaClass.classLoader!!.getResourceAsStream(fileName)
        return inputStream.source().buffer()
    }


    inner class GithubDispatcher : Dispatcher() {

        override fun dispatch(request: RecordedRequest): MockResponse {
            TODO()
        }

    }

}
