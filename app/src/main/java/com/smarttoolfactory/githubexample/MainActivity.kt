package com.smarttoolfactory.githubexample

import android.os.Bundle
import com.smarttoolfactory.githubexample.base.activity.BaseDataBindingActivity
import com.smarttoolfactory.githubexample.databinding.ActivityMainBinding


class MainActivity : BaseDataBindingActivity<ActivityMainBinding>() {


    override val layoutId: Int
        get() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }


}