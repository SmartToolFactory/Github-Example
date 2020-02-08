package com.smarttoolfactory.data.api

import com.smarttoolfactory.data.model.remote.response.RepoDTO
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Retrofit interface that retrieves user repose from Github
 */
interface GithubApi {

    @GET("users/{login}/repos")
    fun getRepoList(@Path("login") login: String): Observable<List<RepoDTO>>

}