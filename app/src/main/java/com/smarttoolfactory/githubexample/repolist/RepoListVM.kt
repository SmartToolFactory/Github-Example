package com.smarttoolfactory.githubexample.repolist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.smarttoolfactory.data.api.Status
import com.smarttoolfactory.domain.GetReposUseCase
import com.smarttoolfactory.domain.model.RepoListItem
import com.smarttoolfactory.githubexample.base.viewmodel.BaseViewModel
import com.smarttoolfactory.githubexample.model.ViewState
import javax.inject.Inject

class RepoListVM @Inject constructor(private val getReposUseCase: GetReposUseCase) :
    BaseViewModel() {

    private val _items = MutableLiveData<List<RepoListItem>>()

    val query = String

    val items: LiveData<List<RepoListItem>> = _items

    val viewState = MutableLiveData<ViewState<RepoListItem>>()

    init {
        getUserRepos()
    }

    fun onRepoClicked(repo: RepoListItem) {

    }

    fun onFavoriteRepoStatusChanded(repo: RepoListItem) {

    }

    fun getUserRepos() {

        viewState.value = ViewState(
            status = Status.LOADING,
            data = null,
            error = null
        )

        _items.value = getRepoListItems()

        viewState.value = ViewState(
            status = Status.SUCCESS,
            data = getRepoListItems(),
            error = null
        )
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


            for (i in 0..5) {

                val repoListItem = RepoListItem(
                    repoId = repoId,
                    repoName = repoName,
                    starCount = repoStars,
                    openIssuesCount = openIssuesCount,
                    ownerId = ownerId,
                    login = login,
                    avatarUrl = avatarUrl,
                    isFavorite = true
                )

                listRepos.add(repoListItem)
            }

            return listRepos


        }

    }

}