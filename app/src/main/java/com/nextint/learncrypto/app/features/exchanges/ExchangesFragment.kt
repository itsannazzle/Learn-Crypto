package com.nextint.learncrypto.app.features.exchanges

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.nextint.learncrypto.app.CryptoApp
import com.nextint.learncrypto.app.MainActivity
import com.nextint.learncrypto.app.R
import com.nextint.learncrypto.app.bases.BaseAdapter
import com.nextint.learncrypto.app.bases.BaseFragment
import com.nextint.learncrypto.app.core.source.remote.response.ExchangesResponseItem
import com.nextint.learncrypto.app.core.source.remote.service.ApiResponse
import com.nextint.learncrypto.app.databinding.FragmentExchangesBinding
import com.nextint.learncrypto.app.features.concept.presentation.TagsViewModel
import com.nextint.learncrypto.app.features.concept.presentation.TagsViewModelFactory
import com.nextint.learncrypto.app.features.exchanges.presentation.ExchangeViewHolder
import com.nextint.learncrypto.app.features.exchanges.presentation.ExchangeViewModel
import com.nextint.learncrypto.app.features.ui.dialog.DialogModel
import com.nextint.learncrypto.app.features.utils.UtilitiesFunction
import com.nextint.learncrypto.app.features.utils.setVertical
import com.nextint.learncrypto.app.util.ID_EXCHANGE_CONSTANT
import com.nextint.learncrypto.app.util.KEY_BUNDLE_MODEL_DIALOG
import com.nextint.learncrypto.app.util.TAG_DIALOG
import javax.inject.Inject


class ExchangesFragment : BaseFragment<ExchangeViewModel>() {

    private var _bindingExchangeFragment : FragmentExchangesBinding? = null
    private val _getBindingExchangeFragment get() = _bindingExchangeFragment
    
    private lateinit var _exchangeAdapter : BaseAdapter<ExchangesResponseItem, ExchangeViewHolder>
    
    override fun setupViewModel(): Class<ExchangeViewModel> = ExchangeViewModel::class.java

    override fun setObserver(): Fragment = this

    @Inject
    lateinit var _factoryTagsViewModel : TagsViewModelFactory
    private val _tagsViewModel : TagsViewModel by viewModels()
    {
        _factoryTagsViewModel
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as CryptoApp).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _bindingExchangeFragment = FragmentExchangesBinding.inflate(inflater,container,false)
        _activityMain = activity as MainActivity
        _modelDialog = DialogModel()
        _viewModel.getAllExchange()
        _activityMain._dialog.show()
        _tagsViewModel.getTagById(getString(R.string.id_exchange))
        return _getBindingExchangeFragment?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        observeLiveData()
        displayView()



    }

    private fun observeLiveData()
    {
        _viewModel.getAllExchange.observe(viewLifecycleOwner)
            {
                response ->
                when(response)
                {
                    is ApiResponse.InternetConnection ->
                    {
                        _modelDialog?.retryActionAlert = { _viewModel.getAllExchange() }
                        _modelDialog?.dialogTitle = R.string.dialog_no_internet_title
                        _modelDialog?.dialogMessage = R.string.dialog_no_internet_message

                        _modelDialog?.let { _activityMain.showDialogFromModelResponseWithRetry(it) }
                    }

                    is ApiResponse.Success ->
                    {
                        response.data?.let()
                        {
                            _activityMain._dialog.hide()
                            _exchangeAdapter.safeClearAndAddAll(response.data)
                        }
                        //setupView()
                    }

                    is ApiResponse.Error ->
                    {
                        if (_dialogFragment.isAdded)
                        {
                            _viewModel.getAllExchange()
                            _dialogFragment.dismiss()
                        }
                        else
                        {
                            _modelDialog?.buttonText = R.string.BUTTON_RETRY
                            _modelDialog?.httpErrorCode = response.message
                            _modelDialog?.retryActionDialog = {
                                _viewModel.getAllExchange()
                            }
                            val bundle = Bundle()
                            bundle.putParcelable(KEY_BUNDLE_MODEL_DIALOG,_modelDialog)
                            _dialogFragment.arguments = bundle
                            _dialogFragment.show(childFragmentManager, TAG_DIALOG)
                        }
                    }

                    is ApiResponse.Empty ->
                    {
                        _modelDialog?.httpErrorCode = 1404
                        val bundle = Bundle()
                        bundle.putParcelable(KEY_BUNDLE_MODEL_DIALOG,_modelDialog)
                        _dialogFragment.arguments = bundle
                        _dialogFragment.show(childFragmentManager, TAG_DIALOG)
                    }

                    else -> _dialogFragment.show(childFragmentManager, TAG_DIALOG)
                }
            }

        _tagsViewModel.tagById.observe(viewLifecycleOwner)
            { response ->
                when(response)
                {
                    is ApiResponse.InternetConnection ->
                    {
                        _modelDialog?.retryActionAlert = { _tagsViewModel.getTagById(getString(R.string.id_exchange)) }
                        _modelDialog?.dialogTitle = R.string.dialog_no_internet_title
                        _modelDialog?.dialogMessage = R.string.dialog_no_internet_message

                        _modelDialog?.let { _activityMain.showDialogFromModelResponseWithRetry(it) }
                    }

                    is ApiResponse.Success ->
                    {
                        response.data?.let()
                        {
                            _getBindingExchangeFragment?.textViewExchangeDesc?.text = response.data.description
                            _getBindingExchangeFragment?.textViewWhatIs?.text = getString(R.string.what_is, response.data.name)
                        }

                    }

                    is ApiResponse.Error ->
                    {
                        if (_dialogFragment.isAdded)
                        {
                            _tagsViewModel.getTagById(getString(R.string.id_exchange))
                            _dialogFragment.dismiss()
                        }
                        else
                        {
                            _modelDialog?.buttonText = R.string.BUTTON_RETRY
                            _modelDialog?.httpErrorCode = response.message
                            _modelDialog?.retryActionDialog = {
                                _tagsViewModel.getTagById(getString(R.string.id_exchange))
                            }
                            val bundle = Bundle()
                            bundle.putParcelable(KEY_BUNDLE_MODEL_DIALOG,_modelDialog)
                            _dialogFragment.arguments = bundle
                            _dialogFragment.show(childFragmentManager, TAG_DIALOG)
                        }
                    }

                    is ApiResponse.Empty ->
                    {
                        _modelDialog?.httpErrorCode = 1404
                        val bundle = Bundle()
                        bundle.putParcelable(KEY_BUNDLE_MODEL_DIALOG,_modelDialog)
                        _dialogFragment.arguments = bundle
                        _dialogFragment.show(childFragmentManager, TAG_DIALOG)
                    }

                    else -> _dialogFragment.show(childFragmentManager,TAG_DIALOG)
                }
            }

//        _viewModel.loading.observe(viewLifecycleOwner,
//            {
//
//            })

        _viewModel.message.observe(viewLifecycleOwner,
            {
                _modelDialog?.dialogMessage = id
                val bundle = Bundle()
                bundle.putParcelable(KEY_BUNDLE_MODEL_DIALOG,_modelDialog)
                _dialogFragment.arguments = bundle
                _dialogFragment.show(childFragmentManager, TAG_DIALOG)
            })

    }



    private fun setupAdapter()
    {
        _exchangeAdapter = BaseAdapter(
            { parent, _ -> ExchangeViewHolder.inflate(parent) },
            { viewHolder, _, item -> viewHolder.bind(item)
            viewHolder.setAction()
            {
                val bundle = Bundle()
                bundle.putString(ID_EXCHANGE_CONSTANT,item.id)
                UtilitiesFunction.replaceFragment(parentFragmentManager, ExchangeDetailFragment(),bundle)
            }
            }
        )
    }

    private fun displayView()
    {
        _getBindingExchangeFragment?.recyclerViewExchange?.apply {
            setVertical()
            adapter = _exchangeAdapter
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _bindingExchangeFragment = null
    }



}