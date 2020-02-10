package com.smarttoolfactory.githubexample.base.activity

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.smarttoolfactory.githubexample.BR
import com.smarttoolfactory.githubexample.base.viewmodel.BaseViewModel

import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class BaseActivity<ViewBinding : ViewDataBinding, VM : BaseViewModel> :
    DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var viewModel: VM
    /**
     * Class of ViewModel to use for key in ViewModelStore
     */
    abstract val viewModelClass: Class<VM>


    lateinit var dataBinding: ViewBinding
    /**
     * Get layout id from the Activity that extends this class
     */
    protected abstract val layoutId: Int


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel =
            ViewModelProvider(this, viewModelFactory).get(viewModelClass)

        dataBinding = DataBindingUtil.setContentView(this, layoutId)
        // Setting lifecycle is required for LiveData to access a LifeCycleOwner instance
        dataBinding.lifecycleOwner = this
        dataBinding.setVariable(BR.viewModel, viewModel)
        dataBinding.executePendingBindings()

    }


    inline fun <reified VM : ViewModel> getVMClass(): Class<VM> = VM::class.java

}