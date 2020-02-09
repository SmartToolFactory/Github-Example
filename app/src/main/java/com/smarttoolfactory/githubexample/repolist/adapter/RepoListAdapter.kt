package com.smarttoolfactory.githubexample.repolist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.smarttoolfactory.domain.model.RepoListItem
import com.smarttoolfactory.githubexample.BR
import com.smarttoolfactory.githubexample.R
import kotlinx.android.synthetic.main.fragment_repo_detail.view.*
import kotlinx.android.synthetic.main.item_repo.view.*


/**
 * Adapter for the currency rates list. Has a reference to the [RepoListVM] to send actions back to it.
 */
class RepoListAdapter(

    private val onItemClicked: (RepoListItem) -> Unit,
    private val onFavoriteStarClicked: (RepoListItem) -> Unit

) :
    ListAdapter<RepoListItem, RepoListAdapter.CustomViewHolder<RepoListItem>>(
        CurrencyRatesDiffCallback()
    ) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CustomViewHolder<RepoListItem> {

        val binding =
            DataBindingUtil.inflate<ViewDataBinding>(
                LayoutInflater.from(parent.context),
                getLayoutRes(),
                parent,
                false
            )

        return CustomViewHolder<RepoListItem>(binding)
            .apply {
                onViewHolderCreated(this, binding)
            }
    }

    /**
     * Add click listener here to prevent setting listener after a ViewHolder every time
     * viewholder is scrolled and onBindViewHolder is called
     */
    private fun onViewHolderCreated(
        viewHolder: RecyclerView.ViewHolder,
        binding: ViewDataBinding
    ) {

        binding.root.setOnClickListener {
            onItemClicked(getItem(viewHolder.adapterPosition))
        }

        binding.root.btn_favorite_repo.setOnClickListener {
            onFavoriteStarClicked(getItem(viewHolder.adapterPosition))
        }

    }


    override fun onBindViewHolder(holder: CustomViewHolder<RepoListItem>, position: Int) {
        val item = getItem(position)
        holder.bindTo(item)
    }

    class CustomViewHolder<T> constructor(
        private val binding: ViewDataBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindTo(
            item: T
        ) {

            // Bind item to layout to dispatch data to layout
            binding.setVariable(BR.item, item)
            binding.executePendingBindings()

        }

    }

    /**
     * get layout res based on view currencyType
     */
    private fun getLayoutRes(): Int {
        return R.layout.item_repo
    }

}

/**
 * Callback for calculating the diff between two non-null items in a list.
 *
 * Used by ListAdapter to calculate the minimum number of changes between and old list and a new
 * list that's been passed to `submitList`.
 */
class CurrencyRatesDiffCallback : DiffUtil.ItemCallback<RepoListItem>() {

    override fun areItemsTheSame(
        oldItem: RepoListItem, newItem: RepoListItem
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: RepoListItem, newItem: RepoListItem
    ): Boolean {
        return oldItem.repoId == newItem.repoId
    }

}
