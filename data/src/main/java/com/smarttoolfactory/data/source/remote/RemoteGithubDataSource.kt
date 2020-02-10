package com.smarttoolfactory.data.source.remote

import com.smarttoolfactory.data.api.GithubApi
import com.smarttoolfactory.data.source.GithubDataSource
import javax.inject.Inject

class RemoteGithubDataSource @Inject constructor(private val webService: GithubApi) :
    GithubDataSource {


}