package com.smarttoolfactory.githubexample

import com.smarttoolfactory.githubexample.base.activity.BaseDataBindingActivity
import com.smarttoolfactory.githubexample.databinding.ActivityMainBinding


class MainActivity : BaseDataBindingActivity<ActivityMainBinding>() {

    override val layoutId: Int
        get() = R.layout.activity_main

}