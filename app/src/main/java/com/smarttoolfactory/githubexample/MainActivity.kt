package com.smarttoolfactory.githubexample

import android.os.Bundle
import android.widget.Toast
import com.smarttoolfactory.githubexample.base.activity.BaseDataBindingActivity
import com.smarttoolfactory.githubexample.databinding.ActivityMainBinding


class MainActivity : BaseDataBindingActivity<ActivityMainBinding>() {


    override val layoutId: Int
        get() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Toast.makeText(this, "MainActivity",Toast.LENGTH_SHORT).show()
    }


}