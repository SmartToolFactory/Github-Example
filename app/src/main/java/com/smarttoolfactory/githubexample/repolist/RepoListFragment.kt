package com.smarttoolfactory.githubexample.repolist

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.smarttoolfactory.githubexample.R
import com.smarttoolfactory.githubexample.base.fragment.BaseFragment
import com.smarttoolfactory.githubexample.databinding.FragmentRepoListBinding
import com.smarttoolfactory.githubexample.repolist.adapter.RepoListAdapter

class RepoListFragment : BaseFragment<FragmentRepoListBinding>() {

    override val layoutId: Int
        get() = R.layout.fragment_repo_list

    private lateinit var repoListVM: RepoListVM


    override fun bindViews() {

        repoListVM =
            ViewModelProvider(activity!!, viewModelFactory).get(RepoListVM::class.java)

        val linearLayoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        dataBinding.viewModel = repoListVM

        dataBinding.recyclerView.apply {
            this.layoutManager = linearLayoutManager
            // Set RecyclerViewAdapter
            this.adapter =
                RepoListAdapter(repoListVM::onRepoClicked, repoListVM::onFavoriteRepoStatusChanged)
        }
    }


}