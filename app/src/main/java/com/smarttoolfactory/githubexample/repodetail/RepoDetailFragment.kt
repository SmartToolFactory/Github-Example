package com.smarttoolfactory.githubexample.repodetail

import androidx.lifecycle.ViewModelProvider
import com.smarttoolfactory.common.observe
import com.smarttoolfactory.domain.model.RepoListItem
import com.smarttoolfactory.githubexample.R
import com.smarttoolfactory.githubexample.base.fragment.BaseFragment
import com.smarttoolfactory.githubexample.databinding.FragmentRepoDetailBinding
import com.smarttoolfactory.githubexample.main.ToolbarVM


/**
 * This fragment and MainActivity share [ToolbarVM] to display shared ViewModels.
 * (activity as MainActivity).toolbar can perform similar actions that is performed via
 * manipulating LiveData of [ToolbarVM]
 */
class RepoDetailFragment : BaseFragment<FragmentRepoDetailBinding>() {


    private lateinit var toolbarVM: ToolbarVM

    override val layoutId: Int
        get() = R.layout.fragment_repo_detail

    private lateinit var repoDetailVM: RepoDetailVM

    override fun bindViews() {

        // Get RepoListItem from navigation component arguments
        val repoItem = arguments?.get("repoItem") as RepoListItem?


        // Set layout via RepoDetailVM
        repoDetailVM =
            ViewModelProvider(activity!!, viewModelFactory).get(RepoDetailVM::class.java)

        dataBinding.viewModel = repoDetailVM


        repoDetailVM.item.value = repoItem


        // Set Toolbar via ToolbarVM
        toolbarVM =
            ViewModelProvider(activity!!, viewModelFactory).get(ToolbarVM::class.java)

        repoItem?.repoName?.let {
            toolbarVM.toolbarTitle.value = it
        }

        toolbarVM.favRepoImgSelected.value = repoItem?.isFavorite

        observe(toolbarVM.saveFavRepoToDb) {
            repoDetailVM.updateFavoriteRepoItem(it)
        }

    }


}