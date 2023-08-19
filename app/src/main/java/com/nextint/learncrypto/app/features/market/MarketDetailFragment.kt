package com.nextint.learncrypto.app.features.market

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.nextint.learncrypto.app.CryptoApp
import com.nextint.learncrypto.app.MainActivity
import com.nextint.learncrypto.app.R
import com.nextint.learncrypto.app.bases.BaseDialogFragment
import com.nextint.learncrypto.app.bases.BaseFragment
import com.nextint.learncrypto.app.core.source.remote.response.MarketsByCoinIdResponseItem
import com.nextint.learncrypto.app.core.source.remote.service.ApiResponse
import com.nextint.learncrypto.app.databinding.FragmentMarketDetailBinding
import com.nextint.learncrypto.app.features.price_converter.presentation.PriceConverterViewModel
import com.nextint.learncrypto.app.features.ui.dialog.DialogModel
import com.nextint.learncrypto.app.features.utils.UtilitiesFunction

import com.nextint.learncrypto.app.features.utils.convertDateToStingPreviewSimple
import com.nextint.learncrypto.app.features.utils.convertStringToDate
import com.nextint.learncrypto.app.util.KEY_BUNDLE_MODEL_DIALOG
import com.nextint.learncrypto.app.util.MODEL_PARCEL_MARKET_BY_ID
import com.nextint.learncrypto.app.util.TAG_DIALOG

class MarketDetailFragment : BaseFragment<PriceConverterViewModel>() {
    private var _bindingFragmentMarketDetail : FragmentMarketDetailBinding? = null
    private val _getBindingFragmentMarketDetail get() = _bindingFragmentMarketDetail
    private var _stringBaseCurrency = ""
    private var _stringQuotedCurrency = ""
    private var _stringAmountToConvert = 0

    override fun setupViewModel(): Class<PriceConverterViewModel> = PriceConverterViewModel::class.java

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
        // Inflate the layout for this fragment
        val model = arguments?.getParcelable<MarketsByCoinIdResponseItem>(MODEL_PARCEL_MARKET_BY_ID)
        _dialogFragment = BaseDialogFragment()
        _activityMain = activity as MainActivity
        _bindingFragmentMarketDetail= FragmentMarketDetailBinding.inflate(layoutInflater,container,false)
        if (model!= null)
        {
            _stringBaseCurrency = model.baseCurrencyId
            _stringQuotedCurrency = model.quoteCurrencyId
            with(_getBindingFragmentMarketDetail)
            {
                this?.textViewMarketDetailPair?.text = model.pair
                this?.textViewMarketDetailMarket?.text = model.exchangeName
                this?.textViewCategory?.text = model.category
                this?.textViewDetailFeeType?.text = model.feeType
                this?.textViewOutlier?.text = getString(UtilitiesFunction.convertBooleanToYesOrNo(model.outlier))
                this?.textViewDetailTrustScore?.text = model.trustScore.uppercase()
                this?.textViewDetailVol24?.text = UtilitiesFunction.convertToUSD(model.quotes.baseKeyPrice.volume24h.toLong())
                this?.tvBaseLastUpdate?.text = getString(R.string.label_base_last_update,model.baseCurrencyName.replaceFirstChar { it.uppercase() })
                this?.tvQuotedLastUpdate?.text = getString(R.string.label_base_last_update,model.quoteCurrencyName.replaceFirstChar { it.uppercase() })
                val stringPrice = model.quotes.baseKeyPrice.price.toString()
                if (stringPrice.first() == '0')
                {
                    this?.textViewDetailPrice?.text = requireContext().getString(R.string.dollar,stringPrice)
                } else
                {
                    this?.textViewDetailPrice?.text = UtilitiesFunction.convertToUSD(model.quotes.baseKeyPrice.price.toLong()).take(8)
                }

                this?.textViewDetailLastUpdate?.text = model.lastUpdated.convertStringToDate() ?: getString(
                    R.string.dash)
                this?.textViewConverter?.text = getString(R.string.convert_a_to_b,model.baseCurrencyName, model.quoteCurrencyName)

            }

            with(_getBindingFragmentMarketDetail)
            {
                this?.buttonConvert?.setOnClickListener()
                {

                    val stringText = _getBindingFragmentMarketDetail?.editTextAmountConvert?.text.toString()
                    try {
                        _viewModel.convertPrice(model.baseCurrencyId , model.quoteCurrencyId ,stringText.toInt())
                    } catch (exception : Exception)
                    {
                        _modelDialog?.dialogTitle = R.string.dialog_input_invalid
                        _modelDialog?.dialogMessage = getString(R.string.dialog_message_invalid)
                        val bundle = Bundle()
                        bundle.putParcelable(KEY_BUNDLE_MODEL_DIALOG, _modelDialog)
                        _dialogFragment.arguments = bundle
                        _dialogFragment.show(childFragmentManager, TAG_DIALOG)
                    }
//                    if (!stringText.isNullOrEmpty())
//                    {
//
//                    } else
//                    {
//
//                    }

                }


            }
        }
        return _getBindingFragmentMarketDetail?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)


        observeLiveData()
    }

    private fun observeLiveData()
    {
        _viewModel.priceConverted.observe(viewLifecycleOwner)
        {
            response ->
            when(response)
            {
                is ApiResponse.InternetConnection ->
                {
                    _modelDialog?.retryActionAlert = { _viewModel.convertPrice(_stringBaseCurrency, _stringQuotedCurrency, _stringAmountToConvert) }
                    _modelDialog?.dialogTitle = R.string.dialog_no_internet_title
                    _modelDialog?.dialogMessage = getString(R.string.dialog_no_internet_message)
                    _modelDialog?.let { _activityMain.showDialogFromModelResponseWithRetry(it) }
                }
                is ApiResponse.Success ->
                {
                    _activityMain._dialog.hide()
                    val data = response.data
                    with(_getBindingFragmentMarketDetail)
                    {
                        this?.tableLayoutConvertResult?.isVisible = true
                        this?.textViewAmountToConvert?.text = data?.amount.toString()
                        this?.textViewConvertedPrice?.text = data?.price?.toLong()
                            ?.let { UtilitiesFunction.convertToUSD(it) }
                        this?.textViewQuotedLastUpdate?.text = data?.quotePriceLastUpdated?.convertStringToDate() ?: getString(R.string.dash)
                        this?.textViewBaseLastUpdate?.text =data?.basePriceLastUpdated?.convertStringToDate() ?: getString(R.string.dash)
                    }
                }
                is ApiResponse.Error ->
                {
                    if (_dialogFragment.isAdded)
                    {
                        _viewModel.convertPrice(_stringBaseCurrency, _stringQuotedCurrency, _stringAmountToConvert)
                        _dialogFragment.dismiss()
                    } else
                    {
                        _modelDialog?.buttonText = R.string.BUTTON_RETRY
                        _modelDialog?.httpErrorCode = response.message
                        _modelDialog?.retryActionDialog =
                            {
                                _viewModel.convertPrice(_stringBaseCurrency, _stringQuotedCurrency, _stringAmountToConvert)
                            }
                        val bundle = Bundle()
                        bundle.putParcelable(KEY_BUNDLE_MODEL_DIALOG, _modelDialog)
                        _dialogFragment.arguments = bundle
                        _dialogFragment.show(childFragmentManager, TAG_DIALOG)
                    }
                }
            }
        }
    }

}