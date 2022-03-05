package com.nextint.learncrypto.app.features.utils

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nextint.learncrypto.app.CryptoApp
import com.nextint.learncrypto.app.MainActivity
import javax.inject.Inject

abstract class BaseFragment<VM : ViewModel> : Fragment() {

    protected lateinit var _activityMain : MainActivity

    @Inject
    lateinit var _factoryViewModel: BaseViewModelFactory

    lateinit var _viewModel : VM


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _viewModel = ViewModelProvider(setObserver(),_factoryViewModel)[setupViewModel()]
    }




//    abstract fun setupInjector()

    abstract fun setupViewModel() : Class<VM>

    abstract fun setObserver() : Fragment

//    abstract fun setData()
//
//    abstract fun setLayout() : Int
}