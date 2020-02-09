package com.smarttoolfactory.githubexample.di.module

import com.smarttoolfactory.githubexample.di.scope.FragmentScope
import com.smarttoolfactory.githubexample.repodetail.RepoDetailFragment
import com.smarttoolfactory.githubexample.repolist.RepoListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * [FragmentContributorModule] is used inside [ActivityContributorModule]
 * With @ContributesAndroidInjector(modules = FragmentContributorModule.class)
 * defines which module will be used to inject objects to declared fragments
 */
@Module
abstract class FragmentContributorModule {

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeRepoListFragment(): RepoListFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeRepoDetailFragment(): RepoDetailFragment
}
