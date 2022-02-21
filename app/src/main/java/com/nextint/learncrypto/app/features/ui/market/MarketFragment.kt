package com.nextint.learncrypto.app.features.ui.market

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import com.nextint.learncrypto.app.CryptoApp
import com.nextint.learncrypto.app.R
import com.nextint.learncrypto.app.core.source.remote.service.ApiResponse
import com.nextint.learncrypto.app.databinding.FragmentMarketBinding
import com.nextint.learncrypto.app.features.market.viewmodel.MarketViewModel
import com.nextint.learncrypto.app.features.market.viewmodel.MarketViewModelFactory
import com.nextint.learncrypto.app.features.utils.BaseFragment
import javax.inject.Inject


class MarketFragment : BaseFragment<MarketViewModel>() {
    private var _bindingMaketFragment : FragmentMarketBinding? = null
    private val _getbindingMaketFragment get() = _bindingMaketFragment

    override fun setupViewModel(): Class<MarketViewModel> = MarketViewModel::class.java

    override fun setObserver(): Fragment = this

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as CryptoApp).appComponent.inject(this)
    }
//    @Inject
//    lateinit var _factoryViewModel : MarketViewModelFactory
//
//    private val _marketViewModel : MarketViewModel by viewModels()
//    {
//        _factoryViewModel
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _viewModel.getMarketByCoin("btc-bitcoin")
        return inflater.inflate(R.layout.fragment_market, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _viewModel.marketByCoin.observe(viewLifecycleOwner,
            {
                response ->
                when(response)
                {
                    is ApiResponse.Success ->
                    {
                        Toast.makeText(requireContext(),"success",Toast.LENGTH_SHORT).show()
                    }
                    is ApiResponse.Error ->
                    {
                        Toast.makeText(requireContext(),response.message,Toast.LENGTH_SHORT).show()
                    }
                    is ApiResponse.Empty ->
                    {
                        Toast.makeText(requireContext(),"empty",Toast.LENGTH_SHORT).show()
                    }
                }
            })
    }

}