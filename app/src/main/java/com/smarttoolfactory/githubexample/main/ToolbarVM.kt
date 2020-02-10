package com.smarttoolfactory.githubexample.main

import androidx.lifecycle.MutableLiveData
import com.smarttoolfactory.common.SingleLiveEvent
import com.smarttoolfactory.domain.model.RepoListItem
import com.smarttoolfactory.githubexample.base.viewmodel.BaseViewModel
import javax.inject.Inject

class ToolbarVM @Inject constructor() : BaseViewModel() {

    var title = "Home"
    /**
     * LiveData to set toolbar image src to selected or unselected
     */
    val favRepoImgSelected = MutableLiveData<Boolean>()

    /**
     * LiveData to trigger save favorite repo task when it's changed via button on toolbar
     */
    val saveFavRepoToDb = SingleLiveEvent<Boolean>()

    fun setFavoriteRepoStatus() {

        // Get current status for favorite repo
        val favRepoStatus = favRepoImgSelected.value?.not()

        // Set toolbar image src based on favorite repo status
        favRepoImgSelected.value = favRepoStatus

        // Signal observer of this live data to update favorite status of repo in db should change
        saveFavRepoToDb.value = favRepoStatus
    }



}