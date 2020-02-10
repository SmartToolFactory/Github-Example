package com.smarttoolfactory.data.source.local


import com.smarttoolfactory.data.model.remote.response.RepoDTO
import com.smarttoolfactory.data.source.GithubDataSource
import com.smarttoolfactory.data.source.local.dao.FavoriteRepoDao
import com.smarttoolfactory.data.source.local.dao.RepoDao
import io.reactivex.Observable
import javax.inject.Inject

class LocalGithubDataSource @Inject constructor(
    private val repoDao: RepoDao,
    private val favoriteRepoDao: FavoriteRepoDao
) :
    GithubDataSource {

    override fun getRepoDTOs(user: String): Observable<List<RepoDTO>> {
        return Observable.empty()
    }

}