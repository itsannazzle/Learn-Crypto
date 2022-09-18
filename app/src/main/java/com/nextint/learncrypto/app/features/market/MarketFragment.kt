package com.nextint.learncrypto.app.features.market

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nextint.learncrypto.app.CryptoApp
import com.nextint.learncrypto.app.MainActivity
import com.nextint.learncrypto.app.R
import com.nextint.learncrypto.app.bases.BaseAdapter
import com.nextint.learncrypto.app.bases.BaseBlankFragment
import com.nextint.learncrypto.app.bases.BaseFragment
import com.nextint.learncrypto.app.core.source.remote.response.MarketsByCoinIdResponseItem
import com.nextint.learncrypto.app.core.source.remote.service.ApiResponse
import com.nextint.learncrypto.app.databinding.FragmentMarketBinding
import com.nextint.learncrypto.app.features.market.presentation.MarketViewHolder
import com.nextint.learncrypto.app.features.market.presentation.MarketViewModel
import com.nextint.learncrypto.app.features.ui.dialog.DialogModel
import com.nextint.learncrypto.app.features.utils.UtilitiesFunction
import com.nextint.learncrypto.app.util.KEY_BUNDLE_DATA
import com.nextint.learncrypto.app.util.KEY_BUNDLE_MODEL_DIALOG
import com.nextint.learncrypto.app.util.MODEL_PARCEL_MARKET_BY_ID
import com.nextint.learncrypto.app.util.TAG_DIALOG


class MarketFragment : BaseFragment<MarketViewModel>() 
{
    private var _bindingMarketFragment : FragmentMarketBinding? = null
    private val _getBindingMarketFragment get() = _bindingMarketFragment
    private lateinit var _bitcoinMarketAdapter : BaseAdapter<MarketsByCoinIdResponseItem, MarketViewHolder>
    private lateinit var _ethMarketAdapter : BaseAdapter<MarketsByCoinIdResponseItem, MarketViewHolder>
    private lateinit var _usdtMarketAdapter : BaseAdapter<MarketsByCoinIdResponseItem, MarketViewHolder>


    override fun setupViewModel(): Class<MarketViewModel> = MarketViewModel::class.java

    override fun setObserver(): Fragment = this

    override fun onAttach(context: Context)
    {
        super.onAttach(context)
        (requireActivity().application as CryptoApp).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        _bindingMarketFragment = FragmentMarketBinding.inflate(layoutInflater,container,false)
        _viewModel.getMarketByCoin(getString(R.string.id_bitcoin))
        _activityMain = activity as MainActivity
        _modelDialog = DialogModel()
        _activityMain._dialog.show()
        return _getBindingMarketFragment?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        displayView()
        observeLiveData()
    }

    private fun setupAdapter()
    {
        _bitcoinMarketAdapter = BaseAdapter(
            { parent, _ -> MarketViewHolder.inflate(parent) },
            { viewHolder, position, item -> viewHolder.bind(item)
            viewHolder.setAction()
            {
                val bundle = Bundle()
                bundle.putParcelable(MODEL_PARCEL_MARKET_BY_ID,item)
                UtilitiesFunction.replaceFragment(parentFragmentManager, MarketDetailFragment(),bundle)
            }
            }
        )

        _ethMarketAdapter = BaseAdapter(
            { parent, _ -> MarketViewHolder.inflate(parent) },
            { viewHolder, _, item -> viewHolder.bind(item)
                viewHolder.setAction()
                {
                    val bundle = Bundle()
                    bundle.putParcelable(MODEL_PARCEL_MARKET_BY_ID,item)
                    UtilitiesFunction.replaceFragment(parentFragmentManager, MarketDetailFragment(),bundle)
                }
            }
        )

        _usdtMarketAdapter = BaseAdapter(
            { parent, _ -> MarketViewHolder.inflate(parent) },
            { viewHolder, _, item -> viewHolder.bind(item)
                viewHolder.setAction()
                {
                    val bundle = Bundle()
                    bundle.putParcelable(MODEL_PARCEL_MARKET_BY_ID,item)
                    UtilitiesFunction.replaceFragment(parentFragmentManager, MarketDetailFragment(),bundle)
                }
            }
        )
    }


    private fun observeLiveData()
    {
        _viewModel.marketByCoin.observe(viewLifecycleOwner
        ) { response ->

            when (response)
            {
                is ApiResponse.InternetConnection ->
                {
                    _modelDialog?.retryActionAlert = { _viewModel.getMarketByCoin(getString(R.string.id_bitcoin)) }
                    _modelDialog?.dialogTitle = R.string.dialog_no_internet_title
                    _modelDialog?.dialogMessage = getString(R.string.dialog_no_internet_message)

                    _modelDialog?.let { _activityMain.showDialogFromModelResponseWithRetry(it) }
                }

                is ApiResponse.Success ->
                {
                    response.data?.let { _bitcoinMarketAdapter.safeClearAndAddAll(response.data) }
                    _viewModel.getMarketByCoin2(getString(R.string.id_eth))
                    val dataBitcoinMarket = response.data
                    var arr = arrayListOf<MarketsByCoinIdResponseItem>()
                    dataBitcoinMarket?.map { arr.addAll(listOf(it)) }
                    val bundle = Bundle()
                    bundle.putParcelableArrayList(KEY_BUNDLE_DATA,arr)
                    _getBindingMarketFragment?.textViewMarketEthMore?.setOnClickListener()
                    {
                        UtilitiesFunction.replaceFragment(parentFragmentManager,BaseBlankFragment(),bundle)
                    }
                }

                is ApiResponse.Error ->
                {
                    if (_dialogFragment.isAdded)
                    {
                        _viewModel.getMarketByCoin(getString(R.string.id_bitcoin))
                        _dialogFragment.dismiss()
                    }
                    else
                    {
                        _modelDialog?.buttonText = R.string.BUTTON_RETRY
                        _modelDialog?.httpErrorCode = response.message
                        _modelDialog?.retryActionDialog = { _viewModel.getMarketByCoin(getString(R.string.id_bitcoin)) }
                        val bundle = Bundle()
                        bundle.putParcelable(KEY_BUNDLE_MODEL_DIALOG, _modelDialog)
                        _dialogFragment.arguments = bundle
                        _dialogFragment.show(childFragmentManager, TAG_DIALOG)
                    }
                }

                is ApiResponse.Empty ->
                {
                    _modelDialog?.httpErrorCode = 1404
                    val bundle = Bundle()
                    bundle.putParcelable(KEY_BUNDLE_MODEL_DIALOG, _modelDialog)
                    _dialogFragment.arguments = bundle
                    _dialogFragment.show(childFragmentManager, TAG_DIALOG)
                }

                else -> _dialogFragment.show(childFragmentManager, TAG_DIALOG)
            }
        }

        _viewModel.marketByCoin2.observe(viewLifecycleOwner
        ) { response ->

            when (response) {
                is ApiResponse.InternetConnection ->
                {
                    _modelDialog?.retryActionAlert = { _viewModel.getMarketByCoin(getString(R.string.id_eth)) }
                    _modelDialog?.dialogTitle = R.string.dialog_no_internet_title
                    _modelDialog?.dialogMessage = getString(R.string.dialog_no_internet_message)

                    _modelDialog?.let { _activityMain.showDialogFromModelResponseWithRetry(it) }
                }

                is ApiResponse.Success ->
                {
                    response.data?.let()
                    {
                        _ethMarketAdapter.safeClearAndAddAll(response.data)
                    }
                    _viewModel.getMarketByCoin3(getString(R.string.id_usdt))
                }

                is ApiResponse.Error ->
                {
                    if (_dialogFragment.isAdded)
                    {
                        _viewModel.getMarketByCoin(getString(R.string.id_eth))
                        _dialogFragment.dismiss()
                    }
                    else
                    {
                        _modelDialog?.buttonText = R.string.BUTTON_RETRY
                        _modelDialog?.httpErrorCode = response.message
                        _modelDialog?.retryActionDialog = { _viewModel.getMarketByCoin(getString(R.string.id_eth)) }
                        val bundle = Bundle()
                        bundle.putParcelable(KEY_BUNDLE_MODEL_DIALOG, _modelDialog)
                        _dialogFragment.arguments = bundle
                        _dialogFragment.show(childFragmentManager, TAG_DIALOG)
                    }
                }

                is ApiResponse.Empty ->
                {
                    _modelDialog?.httpErrorCode = 1404
                    val bundle = Bundle()
                    bundle.putParcelable(KEY_BUNDLE_MODEL_DIALOG, _modelDialog)
                    _dialogFragment.arguments = bundle
                    _dialogFragment.show(childFragmentManager, TAG_DIALOG)
                }

                else -> _dialogFragment.show(childFragmentManager, TAG_DIALOG)
            }
        }

        _viewModel.marketByCoin3.observe(viewLifecycleOwner
        ) { response ->

            when (response) {
                is ApiResponse.InternetConnection ->
                {
                    _modelDialog?.retryActionAlert = { _viewModel.getMarketByCoin(getString(R.string.id_eth)) }
                    _modelDialog?.dialogTitle = R.string.dialog_no_internet_title
                    _modelDialog?.dialogMessage = getString(R.string.dialog_no_internet_message)

                    _modelDialog?.let { _activityMain.showDialogFromModelResponseWithRetry(it) }
                }

                is ApiResponse.Success ->
                {
                    response.data?.let()
                    {
                        _usdtMarketAdapter.safeClearAndAddAll(response.data)
                    }
                    _activityMain._dialog.hide()
                }

                is ApiResponse.Error ->
                {
                    if (_dialogFragment.isAdded)
                    {
                        _viewModel.getMarketByCoin(getString(R.string.id_usdt))
                        _dialogFragment.dismiss()
                    }
                    else
                    {
                        _modelDialog?.buttonText = R.string.BUTTON_RETRY
                        _modelDialog?.httpErrorCode = response.message
                        _modelDialog?.retryActionDialog = { _viewModel.getMarketByCoin(getString(R.string.id_usdt)) }
                        val bundle = Bundle()
                        bundle.putParcelable(KEY_BUNDLE_MODEL_DIALOG, _modelDialog)
                        _dialogFragment.arguments = bundle
                        _dialogFragment.show(childFragmentManager, TAG_DIALOG)
                    }
                }

                is ApiResponse.Empty ->
                {
                    _modelDialog?.httpErrorCode = 1404
                    val bundle = Bundle()
                    bundle.putParcelable(KEY_BUNDLE_MODEL_DIALOG, _modelDialog)
                    _dialogFragment.arguments = bundle
                    _dialogFragment.show(childFragmentManager, TAG_DIALOG)
                }

                else -> _dialogFragment.show(childFragmentManager, TAG_DIALOG)
            }
        }


        _viewModel.message.observe(viewLifecycleOwner
        ) {
            _modelDialog?.dialogMessage = it
            _modelDialog?.httpErrorCode = 422
            val bundle = Bundle()
            bundle.putParcelable(KEY_BUNDLE_MODEL_DIALOG, _modelDialog)
            _dialogFragment.arguments = bundle
            _dialogFragment.show(childFragmentManager, TAG_DIALOG)
        }
    }

    private fun displayView()
    {
        _getBindingMarketFragment?.recyclerViewBitcoinMarket?.apply()
        {
            adapter = _bitcoinMarketAdapter
        }

        _getBindingMarketFragment?.recyclerViewEthMarket?.apply()
        {
            adapter = _ethMarketAdapter
        }
        _getBindingMarketFragment?.recyclerViewTetherMarket?.apply()
        {
            adapter = _usdtMarketAdapter
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        _bindingMarketFragment = null
    }
}