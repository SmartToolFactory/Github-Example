package com.smarttoolfactory.data.source.local


import com.smarttoolfactory.data.source.local.dao.RepoDao
import com.smarttoolfactory.data.source.GithubDataSource
import javax.inject.Inject

class LocalRatesDataSource @Inject constructor(private val repoDao: RepoDao) :
    GithubDataSource {

}