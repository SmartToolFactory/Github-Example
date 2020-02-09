package com.smarttoolfactory.githubexample.repolist

import com.smarttoolfactory.domain.GetReposUseCase
import com.smarttoolfactory.githubexample.base.viewmodel.BaseViewModel
import javax.inject.Inject

class RepoListVM @Inject constructor(private val getReposUseCase: GetReposUseCase) :
    BaseViewModel() {


}