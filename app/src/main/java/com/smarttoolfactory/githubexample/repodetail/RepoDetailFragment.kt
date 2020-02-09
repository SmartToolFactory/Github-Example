package com.smarttoolfactory.githubexample.repodetail

import androidx.lifecycle.ViewModelProvider
import com.smarttoolfactory.githubexample.R
import com.smarttoolfactory.githubexample.base.fragment.BaseFragment
import com.smarttoolfactory.githubexample.databinding.FragmentRepoDetailBinding

class RepoDetailFragment : BaseFragment<FragmentRepoDetailBinding>() {

    override val layoutId: Int
        get() = R.layout.fragment_repo_detail

    private lateinit var repoDetailVM: RepoDetailVM

    override fun bindViews() {

        repoDetailVM =
            ViewModelProvider(activity!!, viewModelFactory).get(RepoDetailVM::class.java)

        dataBinding.viewModel = repoDetailVM

    }
}