package com.nextint.learncrypto.app.features.overview

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nextint.learncrypto.app.BuildConfig
import com.nextint.learncrypto.app.CryptoApp
import com.nextint.learncrypto.app.MainActivity
import com.nextint.learncrypto.app.R
import com.nextint.learncrypto.app.bases.BaseDialogFragment
import com.nextint.learncrypto.app.bases.BaseFragment
import com.nextint.learncrypto.app.core.source.remote.service.ApiResponse
import com.nextint.learncrypto.app.databinding.FragmentHomeBinding
import com.nextint.learncrypto.app.features.coins.CoinsFragment
import com.nextint.learncrypto.app.features.concept.ConceptFragment
import com.nextint.learncrypto.app.features.exchanges.ExchangesFragment
import com.nextint.learncrypto.app.features.market.MarketFragment
import com.nextint.learncrypto.app.features.overview.presentation.OverviewViewModel
import com.nextint.learncrypto.app.features.price_converter.PriceConverterFragment
import com.nextint.learncrypto.app.features.search.presentation.SearchFragment
import com.nextint.learncrypto.app.features.ui.dialog.DialogModel
import com.nextint.learncrypto.app.features.utils.UtilitiesFunction
import com.nextint.learncrypto.app.features.utils.cornerImage
import com.nextint.learncrypto.app.features.utils.loadImage
import com.nextint.learncrypto.app.util.KEY_BUNDLE_MODEL_DIALOG
import com.nextint.learncrypto.app.util.TAG_DIALOG


class HomeFragment : BaseFragment<OverviewViewModel>() {
    private var _bindingHomeFragment : FragmentHomeBinding? = null
    private val _getBindingHomeFragment get() = _bindingHomeFragment

    override fun setupViewModel(): Class<OverviewViewModel> = OverviewViewModel::class.java

    override fun setObserver(): Fragment = this

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as CryptoApp).appComponent.inject(this)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _bindingHomeFragment = FragmentHomeBinding.inflate(layoutInflater,container,false)
        _viewModel.getMarketOverview()
        _dialogFragment = BaseDialogFragment()
        _modelDialog = DialogModel()
        _activityMain = activity as MainActivity
        _activityMain._dialog.show()
        return _getBindingHomeFragment?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        displayView()
        observeLiveData()

    }

    override fun onResume() {
        super.onResume()
        observeLiveData()
    }


    private fun observeLiveData(){
        _viewModel.marketOverview.observe(viewLifecycleOwner
        ) { response ->
            when (response) {
                is ApiResponse.InternetConnection -> {

                    _modelDialog?.retryActionAlert = { _viewModel.getMarketOverview() }
                    _modelDialog?.dialogTitle = R.string.dialog_no_internet_title
                    _modelDialog?.dialogMessage = getString(R.string.dialog_no_internet_message)

                    _modelDialog?.let { _activityMain.showDialogFromModelResponseWithRetry(it) }
                }

                is ApiResponse.Success ->
                {
                    _activityMain._dialog.hide()
                    response.data?.let()
                    {
                        with(_getBindingHomeFragment)
                        {
                            this?.valueMarketCap?.text = UtilitiesFunction.convertToUSD(response.data.marketCapUsd)
                            this?.valueMarketCapATH?.text = UtilitiesFunction.convertToUSD(response.data.marketCapAthValue)
                            this?.valueDiffMarketCapATH?.text = UtilitiesFunction.convertToPercentage(response.data.marketCapChange24h)
                            this?.valueBitcoinDominance?.text = UtilitiesFunction.convertToPercentage(response.data.bitcoinDominancePercentage)
                            this?.valueTotalCrypto?.text = response.data.cryptocurrenciesNumber.toString()
                        }
                    }
                }

                is ApiResponse.Error -> {
                    if (_dialogFragment.isAdded) {
                        _dialogFragment.dismiss()
                        _viewModel.getMarketOverview()
                    } else {
                        val modelDialog = DialogModel()
                        modelDialog.buttonText = R.string.no
                        modelDialog.httpErrorCode = response.message
                        modelDialog.retryActionAlert = {
                            _viewModel.getMarketOverview()
                        }
                        val bundle = Bundle()
                        bundle.putParcelable("MODEL_DIALOG", modelDialog)
                        _dialogFragment.arguments = bundle
                        _dialogFragment.show(childFragmentManager, "TAG")
                    }

                }
                is ApiResponse.Empty -> {
                    _modelDialog?.httpErrorCode = 1404
                    val bundle = Bundle()
                    bundle.putParcelable(KEY_BUNDLE_MODEL_DIALOG, _modelDialog)
                    _dialogFragment.arguments = bundle
                    _dialogFragment.show(childFragmentManager, TAG_DIALOG)
                }
                else -> _dialogFragment.show(childFragmentManager, TAG_DIALOG)
            }
        }
    }


    private fun displayView()
    {
        with(_getBindingHomeFragment?.menuConcept!!){
            textViewTitle.text = getString(R.string.menu_concept)
            imageMenu.clipToOutline = true
            imageMenu.cornerImage("https://images.unsplash.com/photo-1621932953986-15fcf084da0f?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MXx8ZG9nZWNvaW58ZW58MHx8MHx8&auto=format&fit=crop&w=600&q=60",200)
            cardMenu.setOnClickListener()
            {
                //it.background = context?.getDrawable(R.color.primary)
                UtilitiesFunction.replaceFragment(parentFragmentManager, ConceptFragment())
            }
        }
        with(_getBindingHomeFragment?.menuMarket!!){
            textViewTitle.text = getString(R.string.menu_market)
            imageMenu.clipToOutline = true
            imageMenu.cornerImage("https://images.unsplash.com/photo-1621264448270-9ef00e88a935?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8OXx8Y3J5cHRvJTIwbWFya2V0fGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=600&q=60",200)
            cardMenu.setOnClickListener()
            {
                //it.background = context?.getDrawable(R.color.primary)
                UtilitiesFunction.replaceFragment(parentFragmentManager, MarketFragment())
            }
        }
        with(_getBindingHomeFragment?.menuExchanges!!){
            textViewTitle.text = getString(R.string.menu_exchanges)
            imageMenu.clipToOutline = true
            imageMenu.cornerImage("https://images.unsplash.com/photo-1651054558996-03455fe2702f?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1180&q=80",200)
            cardMenu.setOnClickListener {
                //it.background = context?.getDrawable(R.color.primary)
                UtilitiesFunction.replaceFragment(parentFragmentManager, ExchangesFragment())
            }
        }
        with(_getBindingHomeFragment?.menuCoins!!){
            textViewTitle.text = getString(R.string.menu_coins)
            imageMenu.clipToOutline = true
            imageMenu.cornerImage("https://images.unsplash.com/photo-1634024521600-0772a6b89fa8?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=774&q=80",200)
            cardMenu.setOnClickListener {
                //it.background = context?.getDrawable(R.color.primary)
                UtilitiesFunction.replaceFragment(parentFragmentManager, CoinsFragment())
            }
        }
        with(_getBindingHomeFragment?.menuPriceConverter!!){
            textViewTitle.text = getString(R.string.menu_price_converter)
            imageMenu.clipToOutline = true
            imageMenu.cornerImage("https://images.unsplash.com/photo-1625208012722-1a8ab026b846?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8OTZ8fGV0aGVyZXVtfGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=600&q=60",200)
            cardMenu.setOnClickListener {
                //it.background = context?.getDrawable(R.color.primary)
                UtilitiesFunction.replaceFragment(parentFragmentManager, PriceConverterFragment())
            }
        }
        with(_getBindingHomeFragment!!)
        {
            textInputLayoutSearch.setOnClickListener()
            {
                UtilitiesFunction.replaceFragment(parentFragmentManager, SearchFragment())
            }
        }

        _getBindingHomeFragment?.textViewBuildBy?.text = getString(R.string.build_by_anna_karenina_jusuf,"V${BuildConfig.VERSION_NAME}")


    }


    override fun onDestroy() {
        super.onDestroy()
        _bindingHomeFragment = null
    }
}