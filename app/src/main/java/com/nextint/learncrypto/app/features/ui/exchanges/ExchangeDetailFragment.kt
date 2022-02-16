package com.nextint.learncrypto.app.features.ui.exchanges

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.nextint.learncrypto.app.CryptoApp
import com.nextint.learncrypto.app.R
import com.nextint.learncrypto.app.core.source.remote.service.ApiResponse
import com.nextint.learncrypto.app.databinding.FragmentExchangeDetailBinding
import com.nextint.learncrypto.app.features.exchanges.viewmodel.ExchangeViewModel
import com.nextint.learncrypto.app.features.exchanges.viewmodel.ExchangeViewModelFactory
import com.nextint.learncrypto.app.util.ID_EXCHANGE_CONSTANT
import timber.log.Timber
import javax.inject.Inject


class ExchangeDetailFragment : Fragment() {
    private var _bindingExchangeDetail : FragmentExchangeDetailBinding? = null
    private val _getbindingExchangeDetail get() = _bindingExchangeDetail

    @Inject
    lateinit var _factoryExchangeViewModel : ExchangeViewModelFactory
    private val _exchangeViewModel : ExchangeViewModel by viewModels()
    {
        _factoryExchangeViewModel
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
        _bindingExchangeDetail = FragmentExchangeDetailBinding.inflate(inflater,container,false)
        val exchangeId = arguments?.getString(ID_EXCHANGE_CONSTANT)
        _exchangeViewModel.requestGetExchangeById(exchangeId ?: "")
        return _getbindingExchangeDetail?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
    }


    private fun observeData()
    {
        _exchangeViewModel.getExchangeById.observe(viewLifecycleOwner,
            {
                response ->
                when(response)
                {
                    is ApiResponse.Success ->
                    {
                        with(_getbindingExchangeDetail)
                        {
                            response.data.apply {
                                this@with?.textViewDetailExchangeName?.text = name
                            }

                        }
                    }
                    is ApiResponse.Error ->
                        Timber.d(response.message)
                    is ApiResponse.Empty -> Timber.d("empty")
                    else -> Timber.d("else")
                }
            })
    }


}