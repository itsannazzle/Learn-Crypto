package com.nextint.learncrypto.app.features.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.nextint.learncrypto.app.BuildConfig
import com.nextint.learncrypto.app.CryptoApp
import com.nextint.learncrypto.app.R
import com.nextint.learncrypto.app.core.source.remote.service.ApiResponse
import com.nextint.learncrypto.app.databinding.FragmentHomeBinding
import com.nextint.learncrypto.app.features.ui.concept.ConceptFragment
import com.nextint.learncrypto.app.features.overview.viewmodel.OverviewViewModel
import com.nextint.learncrypto.app.features.overview.viewmodel.OverviewViewModelFactory
import com.nextint.learncrypto.app.features.ui.coins.CoinsFragment
import com.nextint.learncrypto.app.features.utils.convertToPercentage
import com.nextint.learncrypto.app.features.utils.convertToUSD
import com.nextint.learncrypto.app.features.utils.replaceFragment
import com.nextint.learncrypto.app.features.utils.setVisibility
import timber.log.Timber
import javax.inject.Inject


class HomeFragment : Fragment() {
    private lateinit var _binding : FragmentHomeBinding

    @Inject
    lateinit var _factoryOverview : OverviewViewModelFactory
    private val _overviewViewModel : OverviewViewModel by viewModels ()
    {
        _factoryOverview
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
        _binding = FragmentHomeBinding.inflate(layoutInflater,container,false)
        _overviewViewModel.getMarketOverview()
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        displayHome()
        setupMarketOverview()

        _overviewViewModel.loading.observe(viewLifecycleOwner,{
            _binding.progressBar.visibility = setVisibility(it)
            Timber.d(it.toString())
        })
    }


    private fun setupMarketOverview(){
        _overviewViewModel.marketOverview.observe(viewLifecycleOwner,{ response ->
            when(response){
                is ApiResponse.Success -> {
                    _binding.textViewCapitalization.text = getString(R.string.crypto_exchange,
                        convertToUSD(response.data.marketCapUsd),
                        convertToUSD(response.data.volume24hUsd),
                        convertToPercentage(response.data.bitcoinDominancePercentage),
                        response.data.cryptocurrenciesNumber.toString())
                }
                is ApiResponse.Error -> {
                    Timber.d(response.message)

                }
                is ApiResponse.Empty -> {
                    Timber.d("empty")
                }
                else -> {
                    Timber.d("else")
                }
            }
        })
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    private fun displayHome()
    {

        with(_binding.menuConcept){
            textViewTitle.text = "Concept"
            textViewNumber.text = "01"
            cardMenu.setOnClickListener()
            {
                it.background = context?.getDrawable(R.color.primary)
                replaceFragment(parentFragmentManager,ConceptFragment())
            }
        }
        with(_binding.menuMarket){
            textViewTitle.text = "Market"
            textViewNumber.text = "03"
        }
        with(_binding.menuExchanges){
            textViewTitle.text = "Exchanges"
            textViewNumber.text = "02"
        }
        with(_binding.menuCoins){
            textViewTitle.text = "Coins"
            textViewNumber.text = "04"
            cardMenu.setOnClickListener {
                replaceFragment(parentFragmentManager,CoinsFragment())
            }
        }
        _binding.textViewBuildBy.text = getString(R.string.build_by_anna_karenina_jusuf,"V${BuildConfig.VERSION_NAME}")

    }
}