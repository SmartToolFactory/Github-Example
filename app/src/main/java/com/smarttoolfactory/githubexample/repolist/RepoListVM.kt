package com.smarttoolfactory.githubexample.repolist

import androidx.lifecycle.MutableLiveData
import com.smarttoolfactory.common.SingleLiveEvent
import com.smarttoolfactory.data.api.Status
import com.smarttoolfactory.data.utils.listenOnMain
import com.smarttoolfactory.domain.GetReposUseCase
import com.smarttoolfactory.domain.model.RepoListItem
import com.smarttoolfactory.githubexample.base.viewmodel.BaseViewModel
import com.smarttoolfactory.githubexample.model.ViewState
import io.reactivex.Observable
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RepoListVM @Inject constructor(private val getReposUseCase: GetReposUseCase) :
    BaseViewModel() {

    val query = MutableLiveData<String>()

    val goToDetailScreen = SingleLiveEvent<RepoListItem>()

    val viewState = MutableLiveData<ViewState<RepoListItem>>()


    fun onRepoClicked(repo: RepoListItem) {
        goToDetailScreen.value = repo
    }

    fun onFavoriteRepoStatusChanged(repo: RepoListItem) {

    }

    fun getUserRepos() {

    }


    /*
        Companion object for mocking list to check UI before writing viewmodel, usecase and other logic
     */
    companion object {

        // Owner properties
        private const val ownerId = 35650605
        private const val login = "SmartToolFactory"
        private const val avatarUrl = "https://avatars0.githubusercontent.com/u/35650605?v=4"

        // Repo properties
        private const val repoId = 169870520
        private const val repoName = "MVVM-Tutorials"
        private const val repoStars = 3
        private const val openIssuesCount = 0

        fun getRepoListItems(): List<RepoListItem> {

            val listRepos = arrayListOf<RepoListItem>()

            val random = 2 + Random().nextInt(9)

            for (i in 0..random) {

                val favorite = i % 2 == 0

                val repoListItem = RepoListItem(
                    repoId = repoId,
                    repoName = repoName,
                    starCount = repoStars,
                    openIssuesCount = openIssuesCount,
                    ownerId = ownerId,
                    login = login,
                    avatarUrl = avatarUrl,
                    isFavorite = favorite
                )

                listRepos.add(repoListItem)
            }

            return listRepos

        }

    }

}