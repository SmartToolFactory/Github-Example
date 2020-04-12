package com.smarttoolfactory.githubexample.repolist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Rule


class RepoListVMTest {

    // Executes each task synchronously using Architecture Components.
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

}