package com.nextint.learncrypto.app.features.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.nextint.learncrypto.app.CryptoApp
import com.nextint.learncrypto.app.R
import com.nextint.learncrypto.app.core.source.remote.ApiResponse
import com.nextint.learncrypto.app.core.source.remote.response.CoinsResponseItem
import com.nextint.learncrypto.app.databinding.FragmentHomeBinding
import com.nextint.learncrypto.app.features.detail.CoinDetailFragment
import com.nextint.learncrypto.app.features.home.viewmodel.HomeViewModel
import com.nextint.learncrypto.app.features.home.viewmodel.HomeViewModelFactory
import com.nextint.learncrypto.app.features.utils.replaceFragment
import com.nextint.learncrypto.app.features.utils.setVertical
import com.nextint.learncrypto.app.features.utils.setVisibility
import com.nextint.learncrypto.app.util.BaseAdapter
import timber.log.Timber
import javax.inject.Inject


class HomeFragment : Fragment() {
    private lateinit var binding : FragmentHomeBinding
    private lateinit var coinAdapter : BaseAdapter<CoinsResponseItem,CoinViewHolder>

    @Inject
    lateinit var factory : HomeViewModelFactory
    private val homeViewModel : HomeViewModel by viewModels {
        factory
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
        binding = FragmentHomeBinding.inflate(layoutInflater,container,false)
        setupAdapter()
        displayHome()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //homeViewModel.getCoins()
        homeViewModel.coins.observe(viewLifecycleOwner,{ response ->
            when(response){
                is ApiResponse.Success -> {
                    coinAdapter.safeAddAll(response.data)
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

        homeViewModel.message.observe(viewLifecycleOwner,{
            Timber.d("msg $it")
        })
//
//        homeViewModel.coinsData.observe(viewLifecycleOwner,{ response ->
//            when(response){
//                is ApiResponse.Success -> coinAdapter.safeAddAll(response.data)
//                is ApiResponse.Empty -> Timber.d("empty")
//                is ApiResponse.Error -> Timber.d("error ${response.message}")
//            }
//        })

        homeViewModel.loading.observe(viewLifecycleOwner,{
            binding.pb.visibility = setVisibility(it)
            Timber.d(it.toString())
        })
    }

    private fun setupAdapter()
    {
        coinAdapter = BaseAdapter({parent, viewType -> CoinViewHolder.inflate(parent) }, {
                viewHolder, _, item ->
            viewHolder.bind(item)
            viewHolder.setCoinAction {
                val bundle = Bundle()
                bundle.putString("ID_COIN",item.id)
                replaceFragment(parentFragmentManager,CoinDetailFragment(),bundle)
            }
        })
    }

    private fun displayHome()
    {
        binding.recyclerView.apply {
            setVertical()
            adapter = coinAdapter
        }
    }
}