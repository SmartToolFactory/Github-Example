package com.smarttoolfactory.data.repository

import com.smarttoolfactory.data.model.local.RepoEntity
import io.reactivex.Observable


interface GithubRepository {

    fun getUserReposOnlineFirst(user: String): Observable<List<RepoEntity>>


}