package com.smarttoolfactory.githubexample.repolist

import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
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

        subscribeGoToDetailScreen()

    }

    private fun subscribeGoToDetailScreen() {

        repoListVM.goToDetailScreen.observe(this, Observer {

            // Create Action
//            val action = RepoListFragmentDirections
//                .actionRepoListFragmentToRepoDetailFragment(it)
//
//            findNavController().navigate(action)

            val bundle = bundleOf("repoItem" to it)
            findNavController().navigate(R.id.action_repoListFragment_to_repoDetailFragment, bundle)
        })

    }

}