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
import com.nextint.learncrypto.app.databinding.FragmentHomeBinding
import com.nextint.learncrypto.app.features.home.viewmodel.HomeViewModel
import com.nextint.learncrypto.app.features.home.viewmodel.HomeViewModelFactory
import com.nextint.learncrypto.app.features.utils.setVisibility
import timber.log.Timber
import javax.inject.Inject


class HomeFragment : Fragment() {
    private lateinit var binding : FragmentHomeBinding

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
//        binding.pb.visibility = View.VISIBLE
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        homeViewModel.getCoins()
//        homeViewModel.coins.observe(viewLifecycleOwner,{ response ->
//            when(response){
//                is ApiResponse.Success -> {
//                    Timber.d(response.data[1].name)
//                }
//                is ApiResponse.Error -> {
//                    Timber.d(response.message)
//
//                }
//                is ApiResponse.Empty -> {
//                    Timber.d("empty")
//                }
//                else -> {
//                    Timber.d("else")
//                }
//            }
//        })

        homeViewModel.message.observe(viewLifecycleOwner,{
            Timber.d("msg $it")
        })

        homeViewModel.coinsData.observe(viewLifecycleOwner,{ response ->
            when(response){
                is ApiResponse.Success -> Timber.d("res suc ${response.data[0].name}")
                is ApiResponse.Empty -> Timber.d("empty")
                is ApiResponse.Error -> Timber.d("error ${response.message}")
            }
        })

        homeViewModel.loading.observe(viewLifecycleOwner,{
            binding.pb.visibility = setVisibility(it)
            Timber.d(it.toString())
        })
    }

}