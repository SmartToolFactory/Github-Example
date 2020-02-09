package com.smarttoolfactory.githubexample.di.module

import com.smarttoolfactory.data.di.DataModule
import dagger.Module


@Module(includes = [DataModule::class, ViewModelModule::class])
class AppModule {


}