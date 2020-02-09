package com.smarttoolfactory.githubexample.util

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.smarttoolfactory.domain.model.RepoListItem
import com.smarttoolfactory.githubexample.R

/*
    *** Bindings for RecyclerView ***
 */

/**
 * [BindingAdapter]s for the [RepoListItem]s to ListAdapter.
 */
@BindingAdapter("app:items")
fun RecyclerView.setItems(items: List<RepoListItem>?) {

    items?.let {
        (adapter as ListAdapter<RepoListItem, *>)?.submitList(items)

    }
}


/**
 * Binding adapter used with this class android:src used with binding of this object
 * loads image from url into specified view
 *
 * @param view image to be loaded into
 * @param path of the image to be fetched
 */
@BindingAdapter("imageSrc")
fun setImageUrl(view: ImageView, path: String?) {

    try {

        val requestOptions = RequestOptions()
        requestOptions.placeholder(R.drawable.ic_launcher_background)


        Glide
            .with(view.context)
            .setDefaultRequestOptions(requestOptions)

            .load(path)
            .into(view)
    } catch (e: Exception) {

    }
}

/**
 * Display or hide a view based on a condition
 */
@BindingAdapter("visibilityBasedOn")
fun View.visibilityBasedOn(condition: Boolean) {
    visibility = if (condition) View.VISIBLE else View.GONE

}

