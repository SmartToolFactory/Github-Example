package com.smarttoolfactory.githubexample.repodetail

import androidx.lifecycle.MutableLiveData
import com.smarttoolfactory.domain.GetReposUseCase
import com.smarttoolfactory.domain.model.RepoListItem
import com.smarttoolfactory.githubexample.base.viewmodel.BaseViewModel
import javax.inject.Inject


class RepoDetailVM @Inject constructor(private val getReposUseCase: GetReposUseCase) :
    BaseViewModel() {

    val item = MutableLiveData<RepoListItem>()


    fun updateFavoriteRepoItem(isFavorite: Boolean) {


    }


}