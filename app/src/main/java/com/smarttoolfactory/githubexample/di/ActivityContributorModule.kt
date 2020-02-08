package com.smarttoolfactory.githubexample.di

import com.smarttoolfactory.githubexample.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityContributorModule {

    /**
    Defines which fragments will be used by [MainActivity]
     */
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity


}
