package com.smarttoolfactory.domain

import com.smarttoolfactory.data.api.DataResult
import com.smarttoolfactory.data.model.local.RepoEntity
import com.smarttoolfactory.data.repository.GithubRepository
import com.smarttoolfactory.data.utils.convertToResultOnIO
import com.smarttoolfactory.domain.base.BaseUseCase
import com.smarttoolfactory.domain.model.RepoListItem
import io.reactivex.Observable
import javax.inject.Inject

/**
 * UseCase class that gets [RepoEntity]'s from [GithubRepository] and dispatches them.
 * Sets favorite status of a repo and clears repose when new fetch request is invoked.
 */
class GetReposUseCase @Inject constructor(private val githubRepository: GithubRepository) :
    BaseUseCase() {

    fun getRepos(user: String): Observable<DataResult<List<RepoListItem>>> {

//        if (user.isNullOrBlank()) return Observable.just(DataResult.Error(Exception("Do not leave query field empty")))

        return githubRepository.getUserReposOnlineFirst(user)
            .map { repoEntityList ->
                mapEntityToItem(repoEntityList)
            }
            .flatMap {
                if (it.isNullOrEmpty()) {
                    Observable.error<List<RepoListItem>>(Exception("No element found"))
                } else {
                    Observable.just(it)
                }
            }

            .convertToResultOnIO()
    }


    private fun mapEntityToItem(repoEntityList: List<RepoEntity>): List<RepoListItem> {
        val list = arrayListOf<RepoListItem>()

        repoEntityList.forEach { repoEntity ->

            val repoListItem = RepoListItem(
                repoId = repoEntity.repoId,
                repoName = repoEntity.repoName,
                starCount = repoEntity.starCount,
                openIssuesCount = repoEntity.openIssuesCount,
                ownerId = repoEntity.ownerId,
                login = repoEntity.login,
                avatarUrl = repoEntity.avatarUrl,
                isFavorite = repoEntity.isFavorite
            )

            list.add(repoListItem)
        }
        return list.toList()
    }

    override fun dispose() {

    }

}