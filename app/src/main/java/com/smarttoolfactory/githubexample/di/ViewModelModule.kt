package com.smarttoolfactory.githubexample.di


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.smarttoolfactory.githubexample.repodetail.RepoDetailVM
import com.smarttoolfactory.githubexample.repolist.RepoListVM
import com.smarttoolfactory.githubexample.viewmodel.CustomViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 *
 * This module is used for retrieving ViewModels using [CustomViewModelFactory], viewModels are put into map
 * via @IntoMap and @ViewModelKey annotations
 *
 * IntoMap creates a key-value pair to get relevant ViewModel. Key is the class name of ViewModel
 * and value is the ViewModel itself
 */
@Module
abstract class ViewModelModule {


    @Binds
    @IntoMap
    @ViewModelKey(RepoListVM::class)
    abstract fun bindRepoListViewModel(loginViewModel: RepoListVM): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RepoDetailVM::class)
    abstract fun bindRepoDetailViewModel(ordersViewModel: RepoDetailVM): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: CustomViewModelFactory): ViewModelProvider.Factory
}
