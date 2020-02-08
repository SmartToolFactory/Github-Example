package com.smarttoolfactory.data.di

import dagger.Module

@Module(includes = [NetworkModule::class, DatabaseModule::class])
class DataModule