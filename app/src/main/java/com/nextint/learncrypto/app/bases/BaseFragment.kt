package com.nextint.learncrypto.app.bases

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nextint.learncrypto.app.MainActivity
import com.nextint.learncrypto.app.features.ui.dialog.BaseDialogFragment
import com.nextint.learncrypto.app.features.ui.dialog.DialogModel
import javax.inject.Inject

abstract class BaseFragment<VM : ViewModel> : Fragment() {

    protected lateinit var _activityMain : MainActivity
    protected lateinit var _dialogFragment : BaseDialogFragment
    protected var _modelDialog : DialogModel? = null

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