package com.smarttoolfactory.githubexample.repolist

import androidx.lifecycle.MutableLiveData
import com.smarttoolfactory.domain.GetReposUseCase
import com.smarttoolfactory.domain.model.RepoListItem
import com.smarttoolfactory.githubexample.base.viewmodel.BaseViewModel
import com.smarttoolfactory.githubexample.model.ViewState
import javax.inject.Inject

class RepoListVM @Inject constructor(private val getReposUseCase: GetReposUseCase) :
    BaseViewModel() {

    val item = MutableLiveData<List<RepoListItem>>()

    val viewState = MutableLiveData<ViewState<List<RepoListItem>>>()


}