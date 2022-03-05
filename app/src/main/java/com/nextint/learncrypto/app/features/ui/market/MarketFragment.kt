package com.nextint.learncrypto.app.features.ui.market

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import com.nextint.learncrypto.app.CryptoApp
import com.nextint.learncrypto.app.MainActivity
import com.nextint.learncrypto.app.R
import com.nextint.learncrypto.app.core.source.remote.response.MarketsByCoinIdResponseItem
import com.nextint.learncrypto.app.core.source.remote.service.ApiResponse
import com.nextint.learncrypto.app.databinding.FragmentMarketBinding
import com.nextint.learncrypto.app.features.market.adapter.MarketViewHolder
import com.nextint.learncrypto.app.features.market.viewmodel.MarketViewModel
import com.nextint.learncrypto.app.features.market.viewmodel.MarketViewModelFactory
import com.nextint.learncrypto.app.features.ui.coins.CoinDetailFragment
import com.nextint.learncrypto.app.features.utils.BaseAdapter
import com.nextint.learncrypto.app.features.utils.BaseFragment
import com.nextint.learncrypto.app.features.utils.UtilitiesFunction
import com.nextint.learncrypto.app.util.ID_COIN_CONSTANT
import com.nextint.learncrypto.app.util.MODEL_PARCEL_MARKET_BY_ID
import timber.log.Timber
import javax.inject.Inject


class MarketFragment : BaseFragment<MarketViewModel>() {
    private var _bindingMaketFragment : FragmentMarketBinding? = null
    private val _getbindingMaketFragment get() = _bindingMaketFragment
    private lateinit var _marketAdapter : BaseAdapter<MarketsByCoinIdResponseItem, MarketViewHolder>

    override fun setupViewModel(): Class<MarketViewModel> = MarketViewModel::class.java

    override fun setObserver(): Fragment = this

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as CryptoApp).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _bindingMaketFragment = FragmentMarketBinding.inflate(layoutInflater,container,false)
        _viewModel.getMarketByCoin("btc-bitcoin")
        _activityMain = activity as MainActivity
        return _getbindingMaketFragment?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        displayView()
        observerMarket()
    }

    private fun setupAdapter()
    {
        _marketAdapter = BaseAdapter({
                parent, _ -> MarketViewHolder.inflate(parent) },{
                viewHolder, position, item ->
            viewHolder.bind(item)
            viewHolder.setAction {
                val bundle = Bundle()
                bundle.putParcelable(MODEL_PARCEL_MARKET_BY_ID,item)
                UtilitiesFunction.replaceFragment(parentFragmentManager, MarketDetailFragment(),bundle)
            }
        }
        )
    }


    private fun observerMarket() {
        _viewModel.marketByCoin.observe(viewLifecycleOwner,
            {
                    response ->

                when (response) {
                    is ApiResponse.InternetConnection ->
                    {
                            _activityMain.showDialogFromModelResponseWithRetry(
                                "Oops, No internet connection",
                                "Please check your connection")
                            {
                               _viewModel.getMarketByCoin("btc-bitcoin")
                            }
                    }
                    is ApiResponse.Success -> {
                        _marketAdapter.safeClearAndAddAll(response.data)
                    }
                    is ApiResponse.Error -> {
                        Toast.makeText(requireContext(), response.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                    is ApiResponse.Empty -> {
                        Toast.makeText(requireContext(), "empty", Toast.LENGTH_SHORT).show()
                    }
                    else -> Toast.makeText(requireContext(), "nothing", Toast.LENGTH_SHORT).show()
                }
            })

        _viewModel.loading.observe(viewLifecycleOwner,
            {
                response ->
                _getbindingMaketFragment?.progressBar2?.visibility = UtilitiesFunction.setVisibility(response)
            })

        _viewModel.message.observe(viewLifecycleOwner,
            {
                Timber.d(it)
            })
    }

    private fun displayView()
    {
        _getbindingMaketFragment?.recyclerViewMarket?.apply {
            adapter = _marketAdapter
        }
    }

}