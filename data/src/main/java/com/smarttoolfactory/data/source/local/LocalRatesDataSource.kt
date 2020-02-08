package com.smarttoolfactory.data.source.local


import com.smarttoolfactory.data.source.GithubDataSource
import com.smarttoolfactory.data.source.local.dao.RepoDao

class LocalRatesDataSource constructor(private val repoDao: RepoDao) :
    GithubDataSource {

}