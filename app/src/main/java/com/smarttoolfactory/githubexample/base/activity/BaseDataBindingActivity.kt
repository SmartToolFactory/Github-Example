package com.smarttoolfactory.githubexample.base.activity

import android.os.Bundle
import android.os.PersistableBundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

/**
 * Abstract Activity class that only responsible of data-binding
 */
abstract class BaseDataBindingActivity<ViewBinding : ViewDataBinding> :
    DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory


    /**
     * Set data-binding
     */
    protected lateinit var dataBinding: ViewBinding
    /**
     * Get layout id from the Activity that extends this class
     */
    protected abstract val layoutId: Int


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataBinding = DataBindingUtil.setContentView(this, layoutId)

        dataBinding.lifecycleOwner = this
        dataBinding.executePendingBindings()
    }


}