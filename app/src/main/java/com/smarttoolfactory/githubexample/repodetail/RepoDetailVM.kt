package com.smarttoolfactory.githubexample.repodetail

import com.smarttoolfactory.domain.GetReposUseCase
import com.smarttoolfactory.githubexample.base.viewmodel.BaseViewModel
import javax.inject.Inject

class RepoDetailVM @Inject constructor(private val getReposUseCase: GetReposUseCase) :
    BaseViewModel() {


}