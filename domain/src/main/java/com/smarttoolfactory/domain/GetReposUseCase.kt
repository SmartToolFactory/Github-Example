package com.smarttoolfactory.domain

import com.smarttoolfactory.data.model.local.RepoEntity
import com.smarttoolfactory.data.repository.GithubRepository
import com.smarttoolfactory.domain.base.BaseUseCase
import javax.inject.Inject

/**
 * UseCase class that gets [RepoEntity]'s from [GithubRepository] and dispatches them.
 * Sets favorite status of a repo and clears repose when new fetch request is invoked.
 */
class GetReposUseCase @Inject constructor(private val githubRepository: GithubRepository):
    BaseUseCase() {

    override fun dispose() {

    }

}