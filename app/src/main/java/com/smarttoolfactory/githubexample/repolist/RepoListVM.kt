package com.smarttoolfactory.githubexample.repolist

import androidx.lifecycle.MutableLiveData
import com.smarttoolfactory.common.SingleLiveEvent
import com.smarttoolfactory.data.api.Status
import com.smarttoolfactory.data.utils.addTo
import com.smarttoolfactory.data.utils.listenOnMain
import com.smarttoolfactory.domain.GetReposUseCase
import com.smarttoolfactory.domain.model.RepoListItem
import com.smarttoolfactory.githubexample.base.viewmodel.BaseViewModel
import com.smarttoolfactory.githubexample.model.ViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
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

        viewState.value = ViewState(
            status = Status.LOADING,
            data = null,
            error = null
        )

        getReposUseCase
            .getRepos(query.value ?: "")
            .listenOnMain()
            .subscribe(
                {
                    viewState.value = ViewState(
                        status = it.status,
                        data = it.data,
                        error = it.error
                    )
                },
                {

                    viewState.value = ViewState(
                        status = Status.ERROR,
                        data = null,
                        error = it
                    )
                    println("onError() $it")
                }

            )
            .addTo(disposables)

    }

}