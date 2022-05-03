package com.nextint.learncrypto.app.features.exchanges

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nextint.learncrypto.app.CryptoApp
import com.nextint.learncrypto.app.MainActivity
import com.nextint.learncrypto.app.R
import com.nextint.learncrypto.app.bases.BaseFragment
import com.nextint.learncrypto.app.core.source.remote.service.ApiResponse
import com.nextint.learncrypto.app.databinding.FragmentExchangeDetailBinding
import com.nextint.learncrypto.app.features.exchanges.presentation.ExchangeViewModel
import com.nextint.learncrypto.app.features.ui.dialog.DialogModel
import com.nextint.learncrypto.app.features.utils.convertDateToStingPreviewSimple
import com.nextint.learncrypto.app.features.utils.convertStringToDate
import com.nextint.learncrypto.app.util.ID_EXCHANGE_CONSTANT
import com.nextint.learncrypto.app.util.KEY_BUNDLE_MODEL_DIALOG
import com.nextint.learncrypto.app.util.TAG_DIALOG


class ExchangeDetailFragment : BaseFragment<ExchangeViewModel>()
{
    private var _bindingExchangeDetail : FragmentExchangeDetailBinding? = null
    private val _getbindingExchangeDetail get() = _bindingExchangeDetail
    private var _exchangeId : String? = null

    override fun setupViewModel(): Class<ExchangeViewModel> = ExchangeViewModel::class.java

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
        _bindingExchangeDetail = FragmentExchangeDetailBinding.inflate(inflater,container,false)
        _exchangeId = arguments?.getString(ID_EXCHANGE_CONSTANT)
        _viewModel.getExchangeById(_exchangeId ?: "")
        _modelDialog = DialogModel()
        _activityMain = activity as MainActivity
        _activityMain._dialog.show()
        return _getbindingExchangeDetail?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        observeLiveData()
    }


    private fun observeLiveData()
    {
        _viewModel.getExchangeById.observe(viewLifecycleOwner
        ) { response ->
            when (response) {
                is ApiResponse.InternetConnection -> {
                    _modelDialog?.retryActionAlert =
                        { _viewModel.getExchangeById(_exchangeId ?: "") }
                    _modelDialog?.dialogTitle = R.string.dialog_no_internet_title
                    _modelDialog?.dialogMessage = R.string.dialog_no_internet_message

                    _modelDialog?.let { _activityMain.showDialogFromModelResponseWithRetry(it) }
                }
                is ApiResponse.Success -> {
                    _activityMain._dialog.hide()
                    with(response.data)
                    {
                        _getbindingExchangeDetail?.textViewDetailExchangeName?.text = name
                        _getbindingExchangeDetail?.indicatorExchangeActive?.textViewStatus?.apply {
                            if (active) text = getString(R.string.active) else visibility =
                                View.GONE
                        }
                        _getbindingExchangeDetail?.textViewAboutExchangeValue?.text = description
                        _getbindingExchangeDetail?.textViewAdjRank?.text = adjustedRank.toString()
                        _getbindingExchangeDetail?.textViewRepRank?.text = reportedRank.toString()
                        _getbindingExchangeDetail?.textViewCurrencies?.text = currencies.toString()
                        _getbindingExchangeDetail?.textViewMarket?.text = markets.toString()
                        val stringFiats = fiats.joinToString { it.symbol }
                        _getbindingExchangeDetail?.textViewFiats?.text = stringFiats
                        _getbindingExchangeDetail?.textViewAdjVol24?.text =
                            quotes.adjustedVolume24h.toString()
                        _getbindingExchangeDetail?.textViewRepVol24?.text =
                            quotes.reportedVolume24h.toString()
                        _getbindingExchangeDetail?.textViewLastUpdate?.text = lastUpdated.convertStringToDate()?.convertDateToStingPreviewSimple()
                        _getbindingExchangeDetail?.textViewScore?.text = confidenceScore.toString()

                    }
                }

                is ApiResponse.Error ->
                    if (_dialogFragment.isAdded) {
                        _viewModel.getExchangeById(_exchangeId ?: "")
                        _dialogFragment.dismiss()
                    } else {
                        _modelDialog?.buttonText = R.string.BUTTON_RETRY
                        _modelDialog?.httpErrorCode = response.message
                        _modelDialog?.retryActionDialog = {
                            _viewModel.getExchangeById(_exchangeId ?: "")
                        }
                        val bundle = Bundle()
                        bundle.putParcelable(KEY_BUNDLE_MODEL_DIALOG, _modelDialog)
                        _dialogFragment.arguments = bundle
                        _dialogFragment.show(childFragmentManager, TAG_DIALOG)
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

        _viewModel.message.observe(viewLifecycleOwner
        ) {
            _modelDialog?.dialogMessage = id
            val bundle = Bundle()
            bundle.putParcelable(KEY_BUNDLE_MODEL_DIALOG, _modelDialog)
            _dialogFragment.arguments = bundle
            _dialogFragment.show(childFragmentManager, TAG_DIALOG)
        }


    }


    override fun onDestroy() {
        super.onDestroy()
        _bindingExchangeDetail = null
    }

}